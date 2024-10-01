package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.categoryController.*;
import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.dto.SubSubCategoryDTO;
import com.example.adventureprogearjava.dto.SubcategoryDTO;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @GetSubSubCategoryById(path = "/subsubcategory/{id}")
    public ResponseEntity<SubSubCategoryDTO> getSubSubCategoryById(@PathVariable Long id) {
        SubSubCategoryDTO subSubCategory = categoryService.getSubSubCategoryById(id);
        return ResponseEntity.ok(subSubCategory);
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

    @CreateSubcategory(path = "/subcategory/{parentCategoryId}")
    public ResponseEntity<Object> createSubCategory(
            @PathVariable("parentCategoryId") Long parentCategoryId,
            @RequestBody SubcategoryDTO subCategoryDTO) {
        try {
            if (subCategoryDTO.getSubcategoryNameEn() == null || subCategoryDTO.getSubcategoryNameUa() == null) {
                throw new IllegalArgumentException("Both category names (English and Ukrainian) are required.");
            }
            categoryService.createSubcategory(parentCategoryId, subCategoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @CreateSubSubCategory(path = "/subsubcategory/{parentSubCategoryId}")
    public ResponseEntity<Object> createSubSubCategory(
            @PathVariable("parentSubCategoryId") Long parentSubCategoryId,
            @RequestBody SubSubCategoryDTO subSubCategoryDTO) {
        try {
            if (subSubCategoryDTO.getSubSubCategoryNameEn() == null || subSubCategoryDTO.getSubSubCategoryNameUa() == null) {
                throw new IllegalArgumentException("Both subsubcategory names (English and Ukrainian) are required.");
            }
            categoryService.createSubSubCategory(parentSubCategoryId, subSubCategoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @UpdateCategory(path = "/{id}")
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long id) {
        try {
            categoryCRUDService.update(categoryDTO, id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @UpdateSubCategory(path = "/subcategory/{id}")
    public ResponseEntity<Object> updateSubCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long id) {
        try {
            categoryService.updateSubcategory(id, categoryDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @UpdateSubSubCategory(path = "/subcategory/subsubcategory/{id}")
    public ResponseEntity<Object> updateSubSubCategory(@RequestBody SubSubCategoryDTO subSubCategoryDTO, @PathVariable("id") Long id) {
        try {
            categoryService.updateSubSubcategory(id, subSubCategoryDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteCategory(path = "/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("id") Long id) {
        try {
            categoryCRUDService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteSubCategory(path = "/subcategory/{id}")
    public ResponseEntity<Object> deleteSubCategory(@PathVariable("id") Long id) {
        try {
            categoryService.deleteSubcategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteSubSubCategory(path = "/subcategory/subsubcategory/{id}")
    public ResponseEntity<Object> deleteSubSubCategory(@PathVariable("id") Long id) {
        try {
            categoryService.deleteSubSubcategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
