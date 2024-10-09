package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.reviewComment.CreateComment;
import com.example.adventureprogearjava.annotation.reviewComment.DeleteComment;
import com.example.adventureprogearjava.annotation.reviewComment.GetAllComment;
import com.example.adventureprogearjava.annotation.reviewComment.GetCommentById;
import com.example.adventureprogearjava.dto.ReviewCommentDTO;
import com.example.adventureprogearjava.exceptions.ReviewAccessDeniedException;
import com.example.adventureprogearjava.exceptions.ReviewCommentAccessDeniedException;
import com.example.adventureprogearjava.exceptions.ReviewCommentNotFoundException;
import com.example.adventureprogearjava.services.ReviewCommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/products/reviews/{reviewId}/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Review Comment Controller",
        description = "API operations for managing comments on product reviews")
public class ReviewCommentController {


    ReviewCommentService reviewCommentService;

    @GetCommentById(path ="/{commentId}")
    public ResponseEntity<ReviewCommentDTO> getCommentById(@PathVariable Long reviewId, @PathVariable Long commentId) {
        try {
            ReviewCommentDTO commentDTO = reviewCommentService.getCommentById(commentId);
            return ResponseEntity.ok(commentDTO);
        } catch (ReviewCommentAccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetAllComment
    public ResponseEntity<List<ReviewCommentDTO>> getAllComments(@PathVariable Long reviewId) {
        List<ReviewCommentDTO> comments = reviewCommentService.getAllCommentsByReviewId(reviewId);
        return ResponseEntity.ok(comments);
    }
    @CreateComment
    public ResponseEntity<ReviewCommentDTO> createComment(@PathVariable Long reviewId,
                                                          @Valid @RequestBody ReviewCommentDTO commentDTO) {
        ReviewCommentDTO savedComment = reviewCommentService.createCommentToReview(reviewId, commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @DeleteComment(path ="/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long reviewId, @PathVariable Long commentId) {
        try {
            reviewCommentService.deleteComment(commentId);
            return ResponseEntity.ok("Comment deleted successfully.");
        } catch (ReviewCommentNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ReviewAccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

}