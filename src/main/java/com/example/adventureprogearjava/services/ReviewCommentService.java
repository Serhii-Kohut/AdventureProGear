package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.ReviewCommentDTO;

import java.util.List;

public interface ReviewCommentService {
    ReviewCommentDTO createCommentToReview(Long reviewId, ReviewCommentDTO commentDTO);
    void deleteComment(Long commentId);

    ReviewCommentDTO getCommentById(Long commentId);

    List<ReviewCommentDTO> getAllCommentsByReviewId(Long reviewId);
}
