package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductReviewDTO;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.entity.ProductReview;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.ReviewNotFoundException;
import com.example.adventureprogearjava.exceptions.ReviewAccessDeniedException;
import com.example.adventureprogearjava.mapper.ProductReviewMapper;
import com.example.adventureprogearjava.repositories.ProductRepository;
import com.example.adventureprogearjava.repositories.ProductReviewRepository;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.ProductReviewCRUDService;
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
public class CRUDProductReviewServiceImpl implements ProductReviewCRUDService {

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
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID:"));
        return productReviewMapper.toDTO(review);
    }

    @Override
    public ProductReviewDTO create(ProductReviewDTO productReviewDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductReview productReview = productReviewMapper.toEntity(productReviewDTO);
        productReview.setDate(LocalDate.now());
        productReview.setUser(user);
        productReview.setUsername(user.getName());

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
    public ProductReviewDTO update(ProductReviewDTO productReviewDTO, Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));

        if (!review.getUser().getEmail().equals(email) && !currentUser.getRole().equals("ROLE_ADMIN")) {
            throw new ReviewAccessDeniedException("You do not have permission to update this review.");
        }

        productReviewMapper.updateEntity(productReviewDTO, review);
        review.setUsername(currentUser.getName());
        ProductReview updatedReview = productReviewRepository.save(review);

        return productReviewMapper.toDTO(updatedReview);
    }

    @Override
    public void delete(Long id) {
        ProductReview review = productReviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!review.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().equals("ROLE_ADMIN")) {
            throw new ReviewAccessDeniedException("You do not have permission to delete this review.");
        }

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
