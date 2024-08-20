package com.example.adventureprogearjava.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDTO {
    @Schema(description = "Category ID", example = "1")
    Long id;

    @Schema(description = "Category name in Ukrainian", example = "Кросівки")
    @NotBlank
    String categoryNameUa;

    @Schema(description = "Category name in English", example = "Sneakers")
    @NotBlank
    String categoryNameEn;

    @Schema(description = "List of subcategories", example ="[]")
    List<CategoryDTO> subcategories;

    @Schema(description = "Self link", example = "/categories/sneakers")
    String selfLink;
}
