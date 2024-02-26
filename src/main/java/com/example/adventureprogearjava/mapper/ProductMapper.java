package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO dto);
}
