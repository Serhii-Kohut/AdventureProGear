package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.Category;
import com.example.adventureprogearjava.entity.enums.Gender;
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
    @NotBlank(message = "Name is mandatory")
    String productNameUa;

    String productNameEn;

    String descriptionUa;

    String descriptionEn;

    @Min(value = 0, message = "Price cannot be negative or blank")
    Long basePrice;

    Gender gender;

    @NotNull(message = "Category is required")
    CategoryDTO category;

    List<ProductAttributeDTO> attributes;

    List<ContentDTO> contents;

    String selfLink;
}
