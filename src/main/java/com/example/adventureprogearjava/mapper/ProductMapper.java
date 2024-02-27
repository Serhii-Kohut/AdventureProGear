package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "basePrice", source = "product.basePrice")
    @Mapping(target = "gender", source = "product.gender")
    @Mapping(target = "category", source = "product.category")
    ProductDTO toDto(Product product);

    @Mapping(target = "productName", source = "dto.productName")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "basePrice", source = "dto.basePrice")
    @Mapping(target = "gender", source = "dto.gender")
    @Mapping(target = "category", source = "dto.category")
    Product toEntity(ProductDTO dto);
}
