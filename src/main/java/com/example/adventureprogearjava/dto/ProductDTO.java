package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.Category;
import com.example.adventureprogearjava.entity.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
    @Schema(description = "Product ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long productId;

    @Schema(description = "Product name_Ua", example = "Кросівки")
    @NotBlank(message = "Name is mandatory")
    String productNameUa;

    @Schema(description = "Product name_En", example = "Sneakers")
    String productNameEn;

    @Schema(description = "Product description_Ua", example = "Зручні спортивні кросівки")
    String descriptionUa;

    @Schema(description = "Product description_En", example = "Comfortable sports sneakers")
    String descriptionEn;

    @Schema(description = "Base price of the product", example = "2000")
    @Min(value = 0, message = "Price cannot be negative or blank")
    Long basePrice;

    @Schema(description = "Gender for whom the product is intended", example = "MALE")
    Gender gender;

    @Schema(description = "Category of the product")
    @NotNull(message = "Category is required")
    CategoryDTO category;

    @Schema(description = "List of attributes associated with the product")
    List<ProductAttributeDTO> attributes;

    @Schema(description = "List of content sources associated with the product")
    List<ContentDTO> contents;

    @Schema(description = "Self link", example = "/products/1")
    String selfLink;
}
