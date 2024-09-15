package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductReviewDTO;
import com.example.adventureprogearjava.entity.ProductReview;
import com.example.adventureprogearjava.mapper.ProductReviewMapper;
import com.example.adventureprogearjava.repositories.ProductReviewRepository;
import com.example.adventureprogearjava.services.ProductReviewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final ProductReviewMapper productReviewMapper;

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

    public void incrementLikes(Long reviewId) {
        ProductReview review = getReviewByIdOrThrow(reviewId);
        review.setLikes(review.getLikes() + 1);
        productReviewRepository.save(review);
    }

    public void incrementDislikes(Long reviewId) {
        ProductReview review = getReviewByIdOrThrow(reviewId);
        review.setDislikes(review.getDislikes() + 1);
        productReviewRepository.save(review);
    }

    public void decrementLikes(Long reviewId) {
        ProductReview review = getReviewByIdOrThrow(reviewId);
        if (review.getLikes() > 0) {
            review.setLikes(review.getLikes() - 1);
            productReviewRepository.save(review);
        } else {
            throw new IllegalStateException("No likes to remove");
        }
    }

    public void decrementDislikes(Long reviewId) {
        ProductReview review = getReviewByIdOrThrow(reviewId);
        if (review.getDislikes() > 0) {
            review.setDislikes(review.getDislikes() - 1);
            productReviewRepository.save(review);
        } else {
            throw new IllegalStateException("No dislikes to remove");
        }
    }

    private ProductReview getReviewByIdOrThrow(Long reviewId) {
        return productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));
    }
}
