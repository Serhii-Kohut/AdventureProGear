package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.dto.SubSubCategoryDTO;
import com.example.adventureprogearjava.dto.SubcategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO getCategoryByName(String name);

    List<CategoryDTO> getAllCategoriesBySection(Long id);

    List<CategoryDTO> getAllSubCategories(Long id);

    CategoryDTO createCategoryWithSection(Long sectionId, CategoryDTO categoryDTO);

    void createSubcategory(Long id, SubcategoryDTO categoryDTO);

    List<CategoryDTO> getAllSubSubCategories(Long id);

    void createSubSubCategory(Long subcategoryId, SubSubCategoryDTO subSubCategoryDTO);

    void updateSubcategory(Long id, CategoryDTO categoryDTO);

    void updateSubSubcategory(Long id, SubSubCategoryDTO categoryDTO);

    void deleteSubcategory(Long id);

    void deleteSubSubcategory(Long id);

    SubSubCategoryDTO getSubSubCategoryById(Long id);
}
