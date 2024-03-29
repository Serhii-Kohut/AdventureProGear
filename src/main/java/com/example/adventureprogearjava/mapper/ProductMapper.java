package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    String api = "towering-house-production.up.railway.app/api/v1/products/";

    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "basePrice", source = "product.basePrice")
    @Mapping(target = "gender", source = "product.gender")
    @Mapping(target = "category", source = "product.category")
    @Mapping(target = "attributes", source = "product.attributes")
    @Mapping(target = "contents", source = "product.contents")
    @Mapping(target = "selfLink", source = "product.id", qualifiedByName = "idToLink")
    ProductDTO toDto(Product product);

    @Mapping(target = "productName", source = "dto.productName")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "basePrice", source = "dto.basePrice")
    @Mapping(target = "gender", source = "dto.gender")
    @Mapping(target = "category", source = "dto.category")
    Product toEntity(ProductDTO dto);

    @Named("idToLink")
    default String getLink(Long id) {
        return api + id;
    }
}
