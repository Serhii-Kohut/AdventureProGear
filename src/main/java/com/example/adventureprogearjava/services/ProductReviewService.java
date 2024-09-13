package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.ProductReviewDTO;

import java.util.List;

public interface ProductReviewService {
    List<ProductReviewDTO> getAll(Long productId, Integer ratingFrom, Integer ratingTo);
}
