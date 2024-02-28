package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.entity.ProductAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {
    ProductAttributeDTO toDto(ProductAttribute productAttribute);

    ProductAttribute toEntity(ProductAttributeDTO dto);
}
