package com.example.adventureprogearjava.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttributeDTO {
    Long id;

    @NotNull(message = "Cannot add product attribute for not existent product")
    Long productId;

    String size;

    String color;

    String additional;

    @NotNull
    Long priceDeviation;

    @NotNull
    Long quantity;

    String label;

    @Schema(description = "Self link", example = "/products/1/attributes/1")
    String selfLink;
}
