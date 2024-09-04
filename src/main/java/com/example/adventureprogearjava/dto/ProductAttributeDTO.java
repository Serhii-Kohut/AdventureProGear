package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Schema(description = "Self link", example = "/products/1/attributes/1")
    String selfLink;
}
