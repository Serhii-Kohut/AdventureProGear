package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ContentDTO;
import com.example.adventureprogearjava.entity.ProductContent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    String api = "https://empowering-happiness-production.up.railway.app/api/v1/productContent/";

    @Mapping(target = "selfLink", source = "content.id", qualifiedByName = "idToLink")
    ContentDTO toDto(ProductContent content);

    ProductContent toEntity(ContentDTO dto);

    @Named("idToLink")
    default String getLink(Long id) {
        return api + id;
    }
}
