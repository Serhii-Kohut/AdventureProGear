package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    @Operation(
            summary = "Get all categories",
            description = "Retrieves all available categories. " +
                    "Categories describe certain group of products"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = CategoryDTO.class))
    )
    public List<CategoryDTO> getAllCategories() {
        return categoryCRUDService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get category by it's own id",
            description = "Retrieves Category by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = CategoryDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public CategoryDTO getCategoryById(
            @Parameter(
                    description = "ID of the category",
                    required = true
            ) @PathVariable("id") Long id) {
        return categoryCRUDService.getById(id);
    }

    @GetMapping("/{name}")
    @Operation(
            summary = "Get category by it's own name",
            description = "Retrieves Category by name"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = CategoryDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public CategoryDTO getCategoryByName(
            @Parameter(
                    description = "Name of the category",
                    required = true
            )
            @PathVariable("name") String name) {
        return categoryService.getCategoryByName(name);
    }

    @GetMapping("subcategory/{id}")
    @Operation(
            summary = "Get subcategories by category id",
            description = "Retrieves all subcategories by category id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = CategoryDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public List<CategoryDTO> getAllSubCategories(
            @Parameter(
                    description = "ID of the category",
                    required = true
            )
            @PathVariable("id") Long id) {
        return categoryService.getAllSubCategories(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiResponse(
            responseCode = "201",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = CategoryDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Creation of new category",
            description = "Creation of new category"
    )
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

    @PostMapping("/subcategory/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiResponse(
            responseCode = "201",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = CategoryDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Creation of new subcategory",
            description = "Creation of new subcategory"
    )
    public CategoryDTO createSubCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category data, required for creation",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))
            )
            @RequestBody CategoryDTO categoryDTO,
            @Parameter(
                    description = "ID of the category",
                    required = true
            )
            @PathVariable("id") Long id) {
        return categoryService.createSubcategory(id, categoryDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Update of the category",
            description = "Update of the category"
    )
    public void updateCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category data, required for creation",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))
            )
            @RequestBody CategoryDTO categoryDTO,
            @Parameter(
                    description = "ID of the category",
                    required = true
            )
            @PathVariable("id") Long id) {
        categoryCRUDService.update(categoryDTO, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = "Deleting category by it's own id",
            description = "Deleting product by it's own id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation."
    )
    @ApiResponse(
            responseCode = "204",
            description = "No content present.",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public void deleteCategory(
            @Parameter(
                    description = "ID of the category",
                    required = true
            )
            @PathVariable("id") Long id) {
        categoryCRUDService.delete(id);
    }


}
