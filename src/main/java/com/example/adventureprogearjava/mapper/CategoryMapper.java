package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.dto.SubSubCategoryDTO;
import com.example.adventureprogearjava.dto.SubcategoryDTO;
import com.example.adventureprogearjava.entity.Category;
import com.example.adventureprogearjava.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);

    @Mapping(target = "section", source = "sectionId")
    Category toEntity(CategoryDTO categoryDTO);

    @Mapping(target = "subcategoryNameUa", source = "category.categoryNameUa")
    @Mapping(target = "subcategoryNameEn", source = "category.categoryNameEn")
    @Mapping(target = "subsectionId", source = "category.id")
    SubcategoryDTO toDTOFromCategory(Category category);

    @Mapping(target = "subSubCategoryNameUa", source = "category.categoryNameUa")
    @Mapping(target = "subSubCategoryNameEn", source = "category.categoryNameEn")
    @Mapping(target = "subCategoryId", source = "category.parentCategory.id")
    @Mapping(target = "selfLink", expression = "java(\"/subsubcategories/\" + category.getId())")
    SubSubCategoryDTO toSubSubCategoryDTO(Category category);

    List<SubcategoryDTO> toSubcategoryDTOs(List<Category> subCategories);

    default Section map(Long sectionId) {
        if (sectionId == null) {
            return null;
        }
        Section section = new Section();
        section.setId(sectionId);
        return section;
    }

}
