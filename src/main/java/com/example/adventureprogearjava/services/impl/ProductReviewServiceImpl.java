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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    public String incrementDislikes(Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));

        boolean hasReacted = productReviewReactionRepository.existsByUserIdAndProductReview(user.getId(), review);
        if (hasReacted) {
            return "User has already reacted to this review";
        }

        review.setDislikes(review.getDislikes() + 1);
        productReviewRepository.save(review);

        ProductReviewReaction reaction = new ProductReviewReaction();
        reaction.setUserId(user.getId());
        reaction.setProductReview(review);
        reaction.setReactionType("DISLIKE");
        productReviewReactionRepository.save(reaction);

        return "Dislike added successfully.";
    }

    public Map<String, String> toggleLike(Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));

        Optional<ProductReviewReaction> reactionOpt = productReviewReactionRepository.findByUserIdAndProductReview(user.getId(), review);
        Map<String, String> response = new HashMap<>();

        if (reactionOpt.isPresent()) {
            ProductReviewReaction reaction = reactionOpt.get();
            if (reaction.getReactionType().equals("LIKE")) {
                // Користувач уже лайкнув, видаляємо лайк
                if (review.getLikes() > 0) {
                    review.setLikes(review.getLikes() - 1);
                    productReviewRepository.save(review);
                }
                productReviewReactionRepository.delete(reaction);
                response.put("action", "unliked");
                response.put("message", "Like removed successfully.");
            } else {
                // Користувач поставив дизлайк, не дозволяємо лайкати
                response.put("action", "error");
                response.put("message", "Cannot like a review you have disliked.");
            }
        } else {
            // Користувач ще не реагував, додаємо лайк
            review.setLikes(review.getLikes() + 1);
            productReviewRepository.save(review);

            ProductReviewReaction newReaction = new ProductReviewReaction(); // Змінено ім'я змінної
            newReaction.setUserId(user.getId());
            newReaction.setProductReview(review);
            newReaction.setReactionType("LIKE");
            productReviewReactionRepository.save(newReaction);

            response.put("action", "liked");
            response.put("message", "Like added successfully.");
        }

        return response;
    }

    public Map<String, String> toggleDislike(Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));

        Optional<ProductReviewReaction> reactionOpt = productReviewReactionRepository.findByUserIdAndProductReview(user.getId(), review);
        Map<String, String> response = new HashMap<>();

        if (reactionOpt.isPresent()) {
            ProductReviewReaction reaction = reactionOpt.get();
            if (reaction.getReactionType().equals("DISLIKE")) {
                // Користувач уже дизлайкнув, видаляємо дизлайк
                if (review.getDislikes() > 0) {
                    review.setDislikes(review.getDislikes() - 1);
                    productReviewRepository.save(review);
                }
                productReviewReactionRepository.delete(reaction);
                response.put("action", "undisliked");
                response.put("message", "Dislike removed successfully.");
            } else {
                // Користувач поставив лайк, не дозволяємо дизлайкати
                response.put("action", "error");
                response.put("message", "Cannot dislike a review you have liked.");
            }
        } else {
            // Користувач ще не реагував, додаємо дизлайк
            review.setDislikes(review.getDislikes() + 1);
            productReviewRepository.save(review);

            ProductReviewReaction newReaction = new ProductReviewReaction();
            newReaction.setUserId(user.getId());
            newReaction.setProductReview(review);
            newReaction.setReactionType("DISLIKE");
            productReviewReactionRepository.save(newReaction);

            response.put("action", "disliked");
            response.put("message", "Dislike added successfully.");
        }

        return response;
    }
}
