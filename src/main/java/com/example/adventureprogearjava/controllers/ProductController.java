package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.ProductService;
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
@RequestMapping("/api/public/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Product Controller",
        description = "API operations for the product catalog")
public class ProductController {
    CRUDService<ProductDTO> productCRUDService;

    ProductService productService;

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Retrieves all available products. " +
                    "Client is also able to request products using following filters: " +
                    " gender, category or price. "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = ProductDTO.class))
    )
    public List<ProductDTO> getAllProducts(
            @Parameter(
                    description = "Gender may be MALE or FEMALE"
            ) @RequestParam(required = false) String gender,
            @Parameter(
                    description = "Name of category(only English provided). " +
                            "Current list of possible categories:" +
                            "T_SHIRTS,\n" +
                            "    PANTS,\n" +
                            "    LINEN,\n" +
                            "    HEADWEARS,\n" +
                            "    HIKING_EQUIPMENT,\n" +
                            "    BAGS,\n" +
                            "    SHOES",
                    required = false
            )
            @RequestParam(required = false) String category,
            @Parameter(
                    description = "Parameter which defines starting price of product.",
                    required = false
            )
            @RequestParam(required = false) Long priceFrom,
            @Parameter(
                    description = "Parameter which defines limiting price of product.",
                    required = false
            )
            @RequestParam(required = false) Long priceTo) {
        if (priceFrom != null && priceTo == null) {
            return productService.getProductsByPriceFrom(priceFrom);
        }

        if (priceTo != null && priceFrom == null) {
            return productService.getProductsByPriceTo(priceTo);
        }

        if (gender != null && category != null
                && (priceFrom != null && priceTo != null))
            return productService
                    .getProductsByPriceAndCategoryAndGender(priceFrom, priceTo, category, gender);
        else if (gender != null && category != null
                && (priceFrom != null && priceTo != null))
            return productService
                    .getProductsByPriceAndCategoryAndGender(priceFrom, priceTo, category, gender);
        else if (category != null
                && (priceFrom != null && priceTo != null))
            return productService
                    .getProductsByPriceAndCategory(priceFrom, priceTo, category);
        else if (gender != null
                && (priceFrom != null && priceTo != null))
            return productService
                    .getProductsByPriceAndGender(priceFrom, priceTo, gender);
        else if (priceFrom != null && priceTo != null)
            return productService.getProductsByPrice(priceFrom, priceTo);
        else if (gender != null && category != null)
            return productService.getProductsByCategoryAndGender(category, gender);
        else if (gender != null)
            return productService.getProductsByGender(gender);
        else if (category != null)
            return productService.getProductsByCategory(category);

        return productCRUDService.getAll();

    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get product by it's own id",
            description = "Retrieves Product by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = ProductDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public ProductDTO getProductById(
            @Parameter(
                    description = "ID of the product",
                    required = true
            )
            @PathVariable Long id) {
        return productCRUDService.getById(id);
    }

    @GetMapping("/name/{name}")
    @Operation(
            summary = "Get all products by name",
            description = "Retrieves all available products with provided name." +
                    "Note: When there are no any product with provided name, service" +
                    "returns just an empty list instead of 404 code status"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = ProductDTO.class))
    )
    public List<ProductDTO> getProductsByName(@PathVariable
                                              @Parameter(
                                                      description = "Name of the product to filter by name",
                                                      required = true
                                              )
                                              String name) {
        return productService.getProductsByName(name);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "201",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = ProductDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Creation of new product",
            description = "Creation of new product"
    )
    public ProductDTO createProduct(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product data, required for creation",
            required = true,
            content = @Content(schema = @Schema(implementation = ProductDTO.class))
    ) @Valid @RequestBody ProductDTO productDTO) {
        return productCRUDService.create(productDTO);
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
            summary = "Update of the product",
            description = "Update of the product"
    )
    public void updateProduct(
            @Parameter(
                    description = "ID of the product",
                    required = true
            ) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product data, required for update",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))
            )
            @Valid @RequestBody ProductDTO productDTO) {
        productCRUDService.update(productDTO, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = "Deleting product by it's own id",
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
    public void deleteProduct(@Parameter(
            description = "ID of the product",
            required = true
    ) @PathVariable Long id) {
        productCRUDService.delete(id);
    }
}
