package com.example.adventureprogearjava.dto;

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
    Long id;

    @Schema(description = "Category name_Uа", example = "Толстовка")
    @NotBlank
    String categoryNameUa;

    @Schema(description = "Category_En", example = "Jumper")
    @NotBlank
    String categoryNameEn;

    @NotBlank
    @Schema(description = "Section ID to which the category belongs", example = "2")
    Long sectionId;
    @Schema(description = "List of subcategories", example ="[]")
    List<CategoryDTO> subcategories;

    @Schema(description = "Self link", example = "/categories/sneakers")
    String selfLink;
}
