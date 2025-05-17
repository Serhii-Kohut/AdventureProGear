package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.productReview.*;
import com.example.adventureprogearjava.dto.ProductReviewDTO;
import com.example.adventureprogearjava.exceptions.ReviewAccessDeniedException;
import com.example.adventureprogearjava.exceptions.ReviewNotFoundException;
import com.example.adventureprogearjava.services.ProductReviewCRUDService;
import com.example.adventureprogearjava.services.ProductReviewService;
import com.example.adventureprogearjava.services.impl.CRUDProductReviewServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/products/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Product Review Controller",
        description = "API operations for managing product reviews")
public class ProductReviewController {

    ProductReviewCRUDService productReviewCRUDService;
    CRUDProductReviewServiceImpl productReviewServiceImpl;
    ProductReviewService productReviewService;

    @com.example.adventureprogearjava.annotation.productReviewController.GetAllReviews(path = "")
    public List<ProductReviewDTO> getAllReviews(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Double ratingFrom,
            @RequestParam(required = false) Double ratingTo) {
        return productReviewService.getAll(productId, ratingFrom, ratingTo);
    }

    @GetReviewById(path = "/{id}")
    public ResponseEntity<ProductReviewDTO> getReviewById(@PathVariable Long id) {
        try {
            ProductReviewDTO reviewDTO = productReviewCRUDService.getById(id);
            return ResponseEntity.ok(reviewDTO);
        } catch (ReviewNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @CreateReview(path = "")
    public ProductReviewDTO createReview(@Valid @RequestBody ProductReviewDTO productReviewDTO) {
        return productReviewCRUDService.create(productReviewDTO);
    }

    @UpdateReview(path = "/{id}")
    public ResponseEntity<String> updateReview(@PathVariable Long id, @Valid @RequestBody ProductReviewDTO productReviewDTO) {
        try {
            ProductReviewDTO updatedReview = productReviewCRUDService.update(productReviewDTO, id);
            return ResponseEntity.ok("Review updated successfully: " + updatedReview);
        } catch (ReviewNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ReviewAccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @DeleteReview(path = "/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        try {
            productReviewCRUDService.delete(id);
            return ResponseEntity.ok("Review deleted successfully.");
        } catch (ReviewNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ReviewAccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @GetAverageRating(path = "/average-rating")
    public double getAverageRating(@RequestParam Long productId) {
        return (Double) productReviewServiceImpl.calculateAverageRating(productId);
    }

    @ToggleLikeReview(path = "/{id}/toggle-like")
    public ResponseEntity<Map<String, String>> toggleLike(@PathVariable Long id) {
        Map<String, String> response = productReviewService.toggleLike(id);
        return ResponseEntity.ok(response);
    }

    @ToggleDislikeReview(path = "/{id}/toggle-dislike")
    public ResponseEntity<Map<String, String>> toggleDislike(@PathVariable Long id) {
        Map<String, String> response = productReviewService.toggleDislike(id);
        return ResponseEntity.ok(response);
    }

}
