package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.CategoryDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {
    CategoryDTO getCategoryByName(String name);

    List<CategoryDTO> getAllCategoriesBySection(Long id);

    List<CategoryDTO> getAllSubCategories(Long id);

    CategoryDTO createCategoryWithSection(Long sectionId, CategoryDTO categoryDTO);

    CategoryDTO createSubcategory(Long id, CategoryDTO categoryDTO);

    List<CategoryDTO> getAllSubSubCategories(Long id);

    CategoryDTO createSubSubCategory(Long subcategoryId, CategoryDTO subSubCategoryDTO);

}
