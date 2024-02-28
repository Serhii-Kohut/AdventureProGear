package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.OrdersList;
import com.example.adventureprogearjava.entity.ProductAttribute;
import com.example.adventureprogearjava.entity.ProductContent;
import com.example.adventureprogearjava.entity.ProductStorage;
import com.example.adventureprogearjava.entity.enums.Gender;
import com.example.adventureprogearjava.entity.enums.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @Min(value = 0, message = "Price cannot be negative")
    Long basePrice;

    Gender gender;

    ProductCategory category;

    List<ProductAttributeDTO> attributes;

    List<ContentDTO> contents;
}
