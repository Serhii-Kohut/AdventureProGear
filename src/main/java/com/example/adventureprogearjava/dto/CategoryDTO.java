package com.example.adventureprogearjava.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Schema(hidden = true)
    Long id;

    @Schema(description = "Category name in Ukrainian", example = "Толстовка")
    @NotBlank
    String categoryNameUa;

    @Schema(description = "Category name in English", example = "Jumper")
    @NotBlank
    String categoryNameEn;

    @Schema(description = "Section ID to which the category belongs, null if this is a subcategory", example = "2")
    Long sectionId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long parentCategoryId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "List of subcategories", example = "[]")
    List<SubcategoryDTO> subcategories;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Self link", example = "/categories/sneakers")
    String selfLink;
}
