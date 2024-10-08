package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.ProductReview;
import com.example.adventureprogearjava.entity.ProductReviewReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductReviewReactionRepository extends JpaRepository<ProductReviewReaction, Long> {
    boolean existsByUserIdAndProductReview(Long userId, ProductReview productReview);
    Optional<ProductReviewReaction> findByUserIdAndProductReview(Long userId, ProductReview productReview);
}
