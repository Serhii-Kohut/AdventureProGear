package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO getCategoryByName(String name);

    List<CategoryDTO> getAllSubCategories(Long id);

    CategoryDTO createSubcategory(Long id, CategoryDTO categoryDTO);
}
