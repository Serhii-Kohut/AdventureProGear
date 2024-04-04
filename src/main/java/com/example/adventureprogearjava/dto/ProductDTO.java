package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.Category;
import com.example.adventureprogearjava.entity.enums.Gender;
import com.example.adventureprogearjava.entity.enums.ProductCategory;
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
    String productName;

    String description;
    @Min(value = 0, message = "Price cannot be negative or blank")
    Long basePrice;

    Gender gender;

    @NotNull(message = "Category is required")
    Category category;

    List<ProductAttributeDTO> attributes;

    List<ContentDTO> contents;

    String selfLink;
}
