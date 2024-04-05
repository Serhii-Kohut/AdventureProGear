package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    String api = "towering-house-production.up.railway.app/api/v1/productContent/";

    @Mapping(target = "selfLink", source = "category.id", qualifiedByName = "idToLink")
    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);

    @Named("idToLink")
    default String getLink(Long id) {
        return api + id;
    }
}
