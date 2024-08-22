package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.services.CRUDService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productAttributes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "ProductAttribute Controller",
        description = "API operations with product attributes")
public class ProductAttributeController {
    CRUDService<ProductAttributeDTO> productAttributeCRUDService;

    @GetMapping
    @Operation(
            summary = "Get all product attributes",
            description = "Retrieves all available product attribute. " +
                    "Any product may have different attributes, like size, color, e.t.c." +
                    "Also this model contains count of available products in the warehouse."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = ProductAttributeDTO.class))
    )
    public List<ProductAttributeDTO> getAllProductAttributes() {
        return productAttributeCRUDService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get product attribute by it's own id",
            description = "Retrieves product attributes by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = ProductAttributeDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public ProductAttributeDTO getProductAttributeById(@Parameter(
            description = "ID of the product attribute",
            required = true
    ) @PathVariable Long id) {
        return productAttributeCRUDService.getById(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "201",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = ProductAttributeDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Creation of new product attribute",
            description = "Creation of new  product attribute. " + "Any product may have different attributes, like size, color, e.t.c." +
                    "Also this model contains count of available products in the warehouse."
    )
    public ProductAttributeDTO createProductAttribute(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product attribute data, required for creation",
            required = true,
            content = @Content(schema = @Schema(implementation = ProductAttributeDTO.class))
    ) @Valid @RequestBody ProductAttributeDTO productAttributeDTO) {
        return productAttributeCRUDService.create(productAttributeDTO);
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
            summary = "Update of the product attribute",
            description = "Update of the product attribute"
    )
    public void updateProductAttribute(@Parameter(
            description = "ID of the product attribute",
            required = true
    ) @PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product attribute, required for creation",
            required = true,
            content = @Content(schema = @Schema(implementation = ProductAttributeDTO.class))
    ) @Valid @RequestBody ProductAttributeDTO productAttributeDTO) {
        productAttributeCRUDService.update(productAttributeDTO, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = "Deleting product attributes by it's own id",
            description = "Deleting product attributes by it's own id"
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
    public void deleteProductAttribute(@Parameter(
            description = "ID of the product attribute",
            required = true
    ) @PathVariable Long id) {
        productAttributeCRUDService.delete(id);
    }
}