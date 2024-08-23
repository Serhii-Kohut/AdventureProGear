package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ContentDTO;
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
@RequestMapping("/api/public/productContent")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "ProductContent Controller",
        description = "API operations with product content")
public class ProductContentController {
    CRUDService<ContentDTO> productContentCRUDService;

    @GetMapping
    @Operation(
            summary = "Get all product contents",
            description = "Retrieves all available product content. Content may be image in different" +
                    "formats: jpg, png, jpeg, webp, e.t.c. "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = ContentDTO.class))
    )
    public List<ContentDTO> getAllProductContent() {
        return productContentCRUDService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get content by it's own id",
            description = "Retrieves Content by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = ContentDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public ContentDTO getProductContentById(
            @Parameter(
                    description = "ID of the content",
                    required = true
            ) @PathVariable Long id) {
        return productContentCRUDService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "201",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = ContentDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Creation of new content",
            description = "Creation of new content. " + " Content may be image in different" +
                    "formats: jpg, png, jpeg, webp, e.t.c."
    )
    public ContentDTO createProductContent(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Content data, required for creation",
            required = true,
            content = @Content(schema = @Schema(implementation = ContentDTO.class))
    ) @Valid @RequestBody ContentDTO contentDTO) {
        return productContentCRUDService.create(contentDTO);
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
            summary = "Update of the content",
            description = "Update of the content"
    )
    public void updateProductContent(@Parameter(
            description = "ID of the content",
            required = true
    ) @PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Content data, required for creation",
            required = true,
            content = @Content(schema = @Schema(implementation = ContentDTO.class))
    ) @Valid @RequestBody ContentDTO contentDTO) {
        productContentCRUDService.update(contentDTO, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = "Deleting product content by it's own id",
            description = "Deleting product content by it's own id"
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
    public void deleteProductContent(@Parameter(
            description = "ID of the content",
            required = true
    ) @PathVariable Long id) {
        productContentCRUDService.delete(id);
    }
}
