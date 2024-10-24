package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ProductReviewDTO;
import com.example.adventureprogearjava.entity.ProductReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductReviewMapper {
    String api = "https://empowering-happiness-production.up.railway.app/api/public/products_review";

    @Mapping(target = "productId", source = "product.id")
    ProductReviewDTO toDTO(ProductReview productReview);

    @Mapping(target = "product.id", source = "productId")
    ProductReview toEntity(ProductReviewDTO productReviewDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    void updateEntity(ProductReviewDTO dto, @MappingTarget ProductReview entity);
}
