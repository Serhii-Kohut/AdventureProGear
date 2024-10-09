package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ReviewCommentDTO;
import com.example.adventureprogearjava.entity.ReviewComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewCommentMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "reviewId", source = "productReview.id")
    ReviewCommentDTO toDTO(ReviewComment reviewComment);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "productReview.id", source = "reviewId")
    ReviewComment toEntity(ReviewCommentDTO reviewCommentDTO);

}
