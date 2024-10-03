package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductReviewDTO;
import com.example.adventureprogearjava.entity.ProductReview;
import com.example.adventureprogearjava.entity.ProductReviewReaction;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.ReviewNotFoundException;
import com.example.adventureprogearjava.mapper.ProductReviewMapper;
import com.example.adventureprogearjava.repositories.ProductReviewReactionRepository;
import com.example.adventureprogearjava.repositories.ProductReviewRepository;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.ProductReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final ProductReviewMapper productReviewMapper;
    private final ProductReviewReactionRepository productReviewReactionRepository;
    private final UserRepository userRepository;

    @Override
    public List<ProductReviewDTO> getAll(Long productId, Double ratingFrom, Double ratingTo) {
        List<ProductReview> reviews;

        if (productId != null) {
            reviews = productReviewRepository.findByProductId(productId);
        } else {
            reviews = productReviewRepository.findAll();
        }

        if (ratingFrom != null && ratingTo != null) {
            reviews = reviews.stream()
                    .filter(review -> review.getRating() >= ratingFrom && review.getRating() <= ratingTo)
                    .collect(Collectors.toList());
        } else if (ratingFrom != null) {
            reviews = reviews.stream()
                    .filter(review -> review.getRating() >= ratingFrom)
                    .collect(Collectors.toList());
        } else if (ratingTo != null) {
            reviews = reviews.stream()
                    .filter(review -> review.getRating() <= ratingTo)
                    .collect(Collectors.toList());
        }

        return reviews.stream()
                .map(productReviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    public String incrementLikes(Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));

        boolean hasReacted = productReviewReactionRepository.existsByUserIdAndReviewId(user.getId(), reviewId);
        if (hasReacted) {
            return "User has already reacted to this review";
        }

        review.setLikes(review.getLikes() + 1);
        productReviewRepository.save(review);

        ProductReviewReaction reaction = new ProductReviewReaction();
        reaction.setUserId(user.getId());
        reaction.setReviewId(reviewId);
        reaction.setReactionType("LIKE");
        productReviewReactionRepository.save(reaction);

        return "Like added successfully.";
    }

    public String incrementDislikes(Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));

        boolean hasReacted = productReviewReactionRepository.existsByUserIdAndReviewId(user.getId(), reviewId);
        if (hasReacted) {
            return "User has already reacted to this review";
        }

        review.setDislikes(review.getDislikes() + 1);
        productReviewRepository.save(review);

        ProductReviewReaction reaction = new ProductReviewReaction();
        reaction.setUserId(user.getId());
        reaction.setReviewId(reviewId);
        reaction.setReactionType("DISLIKE");
        productReviewReactionRepository.save(reaction);

        return "Dislike added successfully.";
    }

    public String decrementLikes(Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));

        Optional<Object> reactionOpt = productReviewReactionRepository.findByUserIdAndReviewId(user.getId(), reviewId);
        if (reactionOpt.isEmpty()) {
            return "Reaction not found";
        }

        ProductReviewReaction reaction = (ProductReviewReaction) reactionOpt.get();
        if (reaction.getReactionType().equals("LIKE")) {
            if (review.getLikes() > 0) {
                review.setLikes(review.getLikes() - 1);
                productReviewRepository.save(review);
            } else {
                return "No likes to remove";
            }

            productReviewReactionRepository.delete(reaction);
            return "Like removed successfully.";
        } else {
            return "User has not liked this review";
        }
    }

    public String decrementDislikes(Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));

        Optional<Object> reactionOpt = productReviewReactionRepository.findByUserIdAndReviewId(user.getId(), reviewId);
        if (reactionOpt.isEmpty()) {
            return "Reaction not found";
        }

        ProductReviewReaction reaction = (ProductReviewReaction) reactionOpt.get();
        if (reaction.getReactionType().equals("DISLIKE")) {
            if (review.getDislikes() > 0) {
                review.setDislikes(review.getDislikes() - 1);
                productReviewRepository.save(review);
            } else {
                return "No dislikes to remove";
            }

            productReviewReactionRepository.delete(reaction);
            return "Dislike removed successfully.";
        } else {
            return "User has not disliked this review";
        }
    }
}
