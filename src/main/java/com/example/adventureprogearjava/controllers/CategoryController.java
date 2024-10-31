package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.categoryController.*;
import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.dto.SubSubCategoryDTO;
import com.example.adventureprogearjava.dto.SubcategoryDTO;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Category Controller", description = "API operations for the category catalog")
public class CategoryController {

    CRUDService<CategoryDTO> categoryCRUDService;
    CategoryService categoryService;

    @GetAllCategory(path = "")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryCRUDService.getAll();
        return ResponseEntity.ok(categories);
    }

    @GetCategoryById(path = "/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id) {
        CategoryDTO category = categoryCRUDService.getById(id);
        return ResponseEntity.ok(category);
    }

    @GetCategoryByName(path = "/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable("name") String name) {
        CategoryDTO category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(category);
    }

    @GetSubcategoryById(path = "/subcategory/{id}")
    public ResponseEntity<List<CategoryDTO>> getAllSubCategories(@PathVariable("id") Long id) {
        List<CategoryDTO> subcategories = categoryService.getAllSubCategories(id);
        return ResponseEntity.ok(subcategories);
    }

    @GetSubSubCategoryById(path = "/subsubcategory/{subcategoryId}")
    public ResponseEntity<List<SubSubCategoryDTO>> getSubSubCategoriesBySubcategoryId(@PathVariable Long subcategoryId) {
        List<SubSubCategoryDTO> subSubCategories = categoryService.getSubSubcategoriesBySubcategoryId(subcategoryId);
        return ResponseEntity.ok(subSubCategories);
    }

    @CreateCategory(path = "")
    public ResponseEntity<Object> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        try {
            if (categoryDTO.getParentCategoryId() == null) {
                categoryDTO.setParentCategoryId(null);
            }

            if (categoryDTO.getSectionId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Section ID is required");
            }

            CategoryDTO createdCategory = categoryService.createCategoryWithSection(categoryDTO.getSectionId(), categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category with this name already exists.");
        }
    }

    @CreateSubcategory(path = "/subcategory/{parentCategoryId}")
    public ResponseEntity<Object> createSubCategory(
            @PathVariable("parentCategoryId") Long parentCategoryId,
            @RequestBody SubcategoryDTO subCategoryDTO) {
        try {
            SubcategoryDTO createdSubcategory = categoryService.createSubcategory(parentCategoryId, subCategoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubcategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both category names (English and Ukrainian) are required.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Subcategory with this name already exists.");
        }
    }

    @CreateSubSubCategory(path = "/subsubcategory/{parentSubCategoryId}")
    public ResponseEntity<Object> createSubSubCategory(
            @PathVariable("parentSubCategoryId") Long parentSubCategoryId,
            @RequestBody SubSubCategoryDTO subSubCategoryDTO) {
        try {
            SubSubCategoryDTO createdSubSubCategory = categoryService.createSubSubCategory(parentSubCategoryId, subSubCategoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubSubCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both subSubcategory names (English and Ukrainian) are required.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("SubSubcategory with this name already exists.");
        }
    }

    @UpdateCategory(path = "/{id}")
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long id) {
        categoryCRUDService.update(categoryDTO, id);
        return ResponseEntity.ok(categoryDTO);
    }

    @UpdateSubCategory(path = "/subcategory/{id}")
    public ResponseEntity<SubcategoryDTO> updateSubCategory(
            @PathVariable("id") Long id,
            @RequestBody SubcategoryDTO subcategoryDTO) {
        SubcategoryDTO updatedSubcategory = categoryService.updateSubcategory(id, subcategoryDTO);
        return ResponseEntity.ok(updatedSubcategory);
    }

    @UpdateSubSubCategory(path = "/subcategory/subSubcategory/{id}")
    public ResponseEntity<SubSubCategoryDTO> updateSubSubCategory(
            @PathVariable("id") Long id,
            @RequestBody SubSubCategoryDTO subSubCategoryDTO) {
        SubSubCategoryDTO updatedSubSubCategory = categoryService.updateSubSubcategory(id, subSubCategoryDTO);
        return ResponseEntity.ok(updatedSubSubCategory);
    }


    @DeleteCategory(path = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryCRUDService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteSubCategory(path = "/subcategory/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable("id") Long id) {
        categoryService.deleteSubcategory(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteSubSubCategory(path = "/subcategory/subSubcategory/{id}")
    public ResponseEntity<Void> deleteSubSubCategory(@PathVariable("id") Long id) {
        categoryService.deleteSubSubcategory(id);
        return ResponseEntity.noContent().build();
    }
}
