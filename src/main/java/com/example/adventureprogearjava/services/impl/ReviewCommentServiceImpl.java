package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ReviewCommentDTO;
import com.example.adventureprogearjava.entity.ProductReview;
import com.example.adventureprogearjava.entity.ReviewComment;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.ReviewCommentAccessDeniedException;
import com.example.adventureprogearjava.exceptions.ReviewCommentNotFoundException;
import com.example.adventureprogearjava.exceptions.ReviewNotFoundException;
import com.example.adventureprogearjava.mapper.ReviewCommentMapper;
import com.example.adventureprogearjava.repositories.ProductReviewRepository;
import com.example.adventureprogearjava.repositories.ReviewCommentRepository;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.MailService;
import com.example.adventureprogearjava.services.ReviewCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewCommentServiceImpl implements ReviewCommentService {

    @Autowired
    private ReviewCommentRepository reviewCommentRepository;

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private ReviewCommentMapper reviewCommentMapper;

    @Override
    public ReviewCommentDTO getCommentById(Long commentId) {
        ReviewComment comment = reviewCommentRepository.findById(commentId)
                .orElseThrow(() -> new ReviewCommentAccessDeniedException("Comment not found"));
        return reviewCommentMapper.toDTO(comment);
    }

    @Override
    public List<ReviewCommentDTO> getAllCommentsByReviewId(Long reviewId) {
        ProductReview productReview = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + reviewId));
        List<ReviewComment> comments = reviewCommentRepository.findByProductReviewId(productReview.getId());
        return comments.stream()
                .map(reviewCommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewCommentDTO createCommentToReview(Long reviewId, ReviewCommentDTO commentDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (review == null) {
            throw new RuntimeException("Review not found for ID: " + reviewId);
        }

        String commentText = commentDTO.getCommentText();

        if (commentText == null || commentText.trim().isEmpty()) {
            throw new RuntimeException("Comment text cannot be null or empty");
        }

        ReviewComment comment = new ReviewComment();
        comment.setCommentText(commentText);
        comment.setCommentDate(LocalDate.now());
        comment.setProductReview(review);
        comment.setUser(user);
        ReviewComment savedComment = reviewCommentRepository.save(comment);
        sendReviewCommentNotification(review.getUser(), review.getId());

        ReviewCommentDTO dto = reviewCommentMapper.toDTO(savedComment);
        dto.setUserId(user.getId());

        return dto;
    }


    private void sendReviewCommentNotification(User reviewAuthor, Long reviewId) {
        String subject = "New Comment on Your Review";
        String message = "Hello " + reviewAuthor.getName() + ",\n\n" +
                "A new comment has been added to your review (ID: " + reviewId + "). " +
                "Please log in to your account to view the comment.\n\n" +
                "Best regards,\nAdventure Pro Gear Team";

        mailService.sendEmail(reviewAuthor.getEmail(), subject, message);
    }

    @Override
    public void deleteComment(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ReviewComment comment = reviewCommentRepository.findById(commentId)
                .orElseThrow(() -> new ReviewCommentNotFoundException("Comment not found with ID: " + commentId));

        if (!comment.getUser().getEmail().equals(currentUser.getEmail()) && !currentUser.getRole().equals("ROLE_ADMIN")) {
            throw new ReviewCommentAccessDeniedException("You do not have permission to delete this comment.");
        }

        reviewCommentRepository.delete(comment);
    }
}
