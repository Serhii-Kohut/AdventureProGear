package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ProductReviewDTO;
import com.example.adventureprogearjava.entity.ProductReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductReviewMapper {
    String api = "http://13.217.73.157:8080/api/public/products_review";

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "date", source = "date")
    ProductReviewDTO toDTO(ProductReview productReview);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "date", source = "date")
    ProductReview toEntity(ProductReviewDTO productReviewDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    void updateEntity(ProductReviewDTO dto, @MappingTarget ProductReview entity);
}
