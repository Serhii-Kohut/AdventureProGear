package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.ProductReviewReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductReviewReactionRepository extends JpaRepository<ProductReviewReaction, Long> {
    boolean existsByUserIdAndReviewId(Long id, Long reviewId);

    Optional<Object> findByUserIdAndReviewId(Long id, Long reviewId);
}
