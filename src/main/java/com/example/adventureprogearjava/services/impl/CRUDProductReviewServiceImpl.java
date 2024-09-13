package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductReviewDTO;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.entity.ProductReview;
import com.example.adventureprogearjava.mapper.ProductReviewMapper;
import com.example.adventureprogearjava.repositories.ProductRepository;
import com.example.adventureprogearjava.repositories.ProductReviewRepository;
import com.example.adventureprogearjava.services.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CRUDProductReviewServiceImpl implements CRUDService<ProductReviewDTO> {

    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private ProductReviewMapper productReviewMapper;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductReviewDTO> getAll() {
        List<ProductReview> reviews = productReviewRepository.findAll();
        return reviews.stream()
                .map(productReviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductReviewDTO getById(Long id) {
        ProductReview review = productReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return productReviewMapper.toDTO(review);
    }

    @Override
    public ProductReviewDTO create(ProductReviewDTO productReviewDTO) {
        ProductReview productReview = productReviewMapper.toEntity(productReviewDTO);
        productReview.setDate(LocalDate.now());
        ProductReview savedReview = productReviewRepository.save(productReview);

        double averageRating = calculateAverageRating(productReviewDTO.getProductId());
        Product product = productRepository.findById(productReviewDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setAverageRating((int) Math.round(averageRating));
        productRepository.save(product);

        return productReviewMapper.toDTO(savedReview);
    }

    @Override
    public void update(ProductReviewDTO productReviewDTO, Long id) {
        ProductReview review = productReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        productReviewMapper.updateEntity(productReviewDTO, review);
        productReviewRepository.save(review);
    }

    @Override
    public void delete(Long id) {
        productReviewRepository.deleteById(id);
    }

    public double calculateAverageRating(Long productId) {
        List<ProductReview> reviews = productReviewRepository.findByProductId(productId);
        if (reviews.isEmpty()) {
            return 0;
        }
        return reviews.stream()
                .mapToInt(productReview -> (int) productReview.getRating())
                .average()
                .orElse(0);
    }
}
