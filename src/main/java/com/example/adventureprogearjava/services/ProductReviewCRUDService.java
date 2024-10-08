package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.ProductReviewDTO;

import java.util.List;

public interface ProductReviewCRUDService {
    List<ProductReviewDTO> getAll();

    ProductReviewDTO getById(Long id);

    ProductReviewDTO create(ProductReviewDTO productReviewDTO);

    ProductReviewDTO update(ProductReviewDTO productReviewDTO, Long reviewId);

    void delete(Long id);
}