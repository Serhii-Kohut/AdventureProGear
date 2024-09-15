package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductReviewDTO;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.entity.ProductReview;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.mapper.ProductReviewMapper;
import com.example.adventureprogearjava.repositories.ProductRepository;
import com.example.adventureprogearjava.repositories.ProductReviewRepository;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CRUDProductReviewServiceImpl implements CRUDService<ProductReviewDTO> {

    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private ProductReviewMapper productReviewMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String username = user.getName();
        ProductReview productReview = productReviewMapper.toEntity(productReviewDTO);
        productReview.setDate(LocalDate.now());
        productReview.setUsername(username);
        if (productReview.getLikes() == 0 && productReviewDTO.getLikes() != 0) {
            productReview.setLikes(productReviewDTO.getLikes());
        }
        if (productReview.getDislikes() == 0 && productReviewDTO.getDislikes() != 0) {
            productReview.setDislikes(productReviewDTO.getDislikes());
        }
        ProductReview savedReview = productReviewRepository.save(productReview);

        double averageRating = calculateAverageRating(productReviewDTO.getProductId());
        Product product = productRepository.findById(productReviewDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.updateAverageRating(product.getId(), averageRating);

        return productReviewMapper.toDTO(savedReview);
    }

    @Override
    public void update(ProductReviewDTO productReviewDTO, Long id) {
        ProductReview review = productReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        productReviewMapper.updateEntity(productReviewDTO, review);
        productReviewRepository.save(review);

        double averageRating = calculateAverageRating(review.getProduct().getId());
        Product product = productRepository.findById(review.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setAverageRating(averageRating);
        productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productReviewRepository.deleteById(id);
    }

    public double calculateAverageRating(Long productId) {
        List<ProductReview> reviews = productReviewRepository.findByProductId(productId);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double average = reviews.stream()
                .mapToDouble(ProductReview::getRating)
                .average()
                .orElse(0.0);

        BigDecimal roundedAverage = new BigDecimal(average).setScale(1, RoundingMode.HALF_UP);
        return roundedAverage.doubleValue();
    }
}
