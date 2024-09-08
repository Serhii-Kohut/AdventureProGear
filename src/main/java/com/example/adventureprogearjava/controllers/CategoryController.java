package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.categoryController.*;
import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Category Controller",
        description = "API operations for the category catalog")
public class CategoryController {
    CRUDService<CategoryDTO> categoryCRUDService;

    CategoryService categoryService;

    @GetAllCategory(path = "")
    public List<CategoryDTO> getAllCategories() {
        return categoryCRUDService.getAll();
    }

    @GetCategoryById(path = "/{id}")
    public CategoryDTO getCategoryById(@PathVariable("id") Long id) {
        return categoryCRUDService.getById(id);
    }

    @GetCategoryByName(path = "/{name}")
    public CategoryDTO getCategoryByName(@PathVariable("name") String name) {
        return categoryService.getCategoryByName(name);
    }

    @GetSubcategoryById(path = "subcategory/{id}")
    public List<CategoryDTO> getAllSubCategories(@PathVariable("id") Long id) {
        return categoryService.getAllSubCategories(id);
    }

    @CreateCategory(path = "")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            Long sectionId = categoryDTO.getSectionId();
            if (sectionId == null) {
                throw new IllegalArgumentException("Section ID is required");
            }
            CategoryDTO createdCategory = categoryService.createCategoryWithSection(sectionId, categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category with name: " + categoryDTO.getCategoryNameEn() + " already exists.");
        }
    }

    @CreateSubcategory(path = "/subcategory/{id}")
    public CategoryDTO createSubCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long id) {
        return categoryService.createSubcategory(id, categoryDTO);
    }

    @UpdateCategory(path = "/{id}")
    public void updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long id) {
        categoryCRUDService.update(categoryDTO, id);
    }

    @DeleteCategory(path = "/{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryCRUDService.delete(id);
    }
}
