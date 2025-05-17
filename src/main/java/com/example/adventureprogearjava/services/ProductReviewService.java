package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.ProductReviewDTO;

import java.util.List;
import java.util.Map;

public interface ProductReviewService {
    List<ProductReviewDTO> getAll(Long productId, Double ratingFrom, Double ratingTo);

    Map<String, String> toggleLike(Long reviewId);

    Map<String, String> toggleDislike(Long reviewId);
}
