package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.ProductReviewDTO;

import java.util.List;

public interface ProductReviewService {
    List<ProductReviewDTO> getAll(Long productId, Double ratingFrom, Double ratingTo);

    String incrementLikes(Long id);

    String incrementDislikes(Long id);

    String decrementLikes(Long id);

    String decrementDislikes(Long id);
}
