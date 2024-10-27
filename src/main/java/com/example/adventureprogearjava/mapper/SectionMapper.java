package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.SectionDTO;
import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.dto.SubcategoryDTO;
import com.example.adventureprogearjava.dto.SubSubCategoryDTO;
import com.example.adventureprogearjava.entity.Section;
import com.example.adventureprogearjava.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SectionMapper {
    String api = "https://empowering-happiness-production.up.railway.app/api/public/sections";
    String categoriesApi = "https://prime-tax-production.up.railway.app/api/public/categories/";

    @Mapping(target = "selfLink", source = "section.id", qualifiedByName = "idToLink")
    @Mapping(target = "categoryCreationLink", source = "section.id", qualifiedByName = "idToCategoryLink")
    SectionDTO toDto(Section section);

    Section toEntity(SectionDTO dto);

    @Named("idToLink")
    default String getLink(Long id) {
        return api + "/" + id;
    }

    @Named("idToCategoryLink")
    default String getCategoryLink(Long id) {
        return categoriesApi + id;
    }

    default List<CategoryDTO> mapCategories(List<Category> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());
    }

    default CategoryDTO toCategoryDTO(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryDTO.builder()
                .id(category.getId())
                .categoryNameUa(category.getCategoryNameUa())
                .categoryNameEn(category.getCategoryNameEn())
                .parentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .subcategories(mapSubcategories(category.getSubcategories()))
                .sectionId(category.getSection() != null ? category.getSection().getId() : null)
                .selfLink(getCategoryLink(category.getId()))
                .build();
    }

    default List<SubcategoryDTO> mapSubcategories(List<Category> subcategories) {
        if (subcategories == null) {
            return null;
        }
        return subcategories.stream()
                .map(this::toSubcategoryDTO)
                .collect(Collectors.toList());
    }
    default SubcategoryDTO toSubcategoryDTO(Category category) {
        if (category == null) {
            return null;
        }
        return SubcategoryDTO.builder()
                .id(category.getId())
                .subcategoryNameUa(category.getCategoryNameUa())
                .subcategoryNameEn(category.getCategoryNameEn())
                .parentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .subSubCategories(mapSubSubCategories(category.getSubcategories()))
                .build();
    }

    default List<SubSubCategoryDTO> mapSubSubCategories(List<Category> subSubCategories) {
        if (subSubCategories == null) {
            return null;
        }
        return subSubCategories.stream()
                .filter(category -> category.getParentCategory() != null && category.getParentCategory().getId() != null)
                .map(this::toSubSubCategoryDTO)
                .collect(Collectors.toList());
    }

    default SubSubCategoryDTO toSubSubCategoryDTO(Category category) {
        if (category == null) {
            return null;
        }
        return SubSubCategoryDTO.builder()
                .id(category.getId())
                .subSubCategoryNameUa(category.getCategoryNameUa())
                .subSubCategoryNameEn(category.getCategoryNameEn())
                .subCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .build();
    }
}
