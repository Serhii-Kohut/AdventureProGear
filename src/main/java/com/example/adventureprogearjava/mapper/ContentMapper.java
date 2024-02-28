package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ContentDTO;
import com.example.adventureprogearjava.entity.ProductContent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    ContentDTO toDto(ProductContent content);

    ProductContent toEntity(ContentDTO dto);
}
