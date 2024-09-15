package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ProductReviewDTO;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.ProductReviewService;
import com.example.adventureprogearjava.services.impl.CRUDProductReviewServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/products/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Product Review Controller",
        description = "API operations for managing product reviews")
public class ProductReviewController {

    CRUDService<ProductReviewDTO> productReviewCRUDService;
    CRUDProductReviewServiceImpl productReviewServiceImpl;
    ProductReviewService productReviewService;

    @GetMapping("")
    public List<ProductReviewDTO> getAllReviews(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Double ratingFrom,
            @RequestParam(required = false) Double ratingTo) {
        return productReviewService.getAll(productId, ratingFrom, ratingTo);
    }

    @GetMapping("/{id}")
    public ProductReviewDTO getReviewById(@PathVariable Long id) {
        return productReviewCRUDService.getById(id);
    }

    @PostMapping("")
    public ProductReviewDTO createReview(@Valid @RequestBody ProductReviewDTO productReviewDTO) {
        return productReviewCRUDService.create(productReviewDTO);
    }

    @PutMapping("/{id}")
    public void updateReview(@PathVariable Long id, @Valid @RequestBody ProductReviewDTO productReviewDTO) {
        productReviewCRUDService.update(productReviewDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        productReviewCRUDService.delete(id);
    }

    @GetMapping("/average-rating")
    public double getAverageRating(@RequestParam Long productId) {
        return (Double) productReviewServiceImpl.calculateAverageRating(productId);
    }
    @PostMapping("/{id}/like")
    public ResponseEntity<?> likeReview(@PathVariable Long id) {
        productReviewService.incrementLikes(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<?> dislikeReview(@PathVariable Long id) {
        productReviewService.incrementDislikes(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<?> unlikeReview(@PathVariable Long id) {
        productReviewService.decrementLikes(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/undislike")
    public ResponseEntity<?> undislikeReview(@PathVariable Long id) {
        productReviewService.decrementDislikes(id);
        return ResponseEntity.ok().build();
    }
}