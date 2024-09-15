package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.ProductReviewDTO;

import java.util.List;

public interface ProductReviewService {
    List<ProductReviewDTO> getAll(Long productId, Double ratingFrom, Double ratingTo);

    void incrementLikes(Long id);

    void incrementDislikes(Long id);

    void decrementLikes(Long id);

    void decrementDislikes(Long id);
}
