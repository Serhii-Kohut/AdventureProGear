package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.dto.ProductStorageDTO;
import com.example.adventureprogearjava.entity.ProductAttribute;
import com.example.adventureprogearjava.entity.ProductStorage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductStorageMapper {
    ProductStorageDTO toDto(ProductStorage productAttribute);

    ProductStorage toEntity(ProductStorageDTO dto);
}
