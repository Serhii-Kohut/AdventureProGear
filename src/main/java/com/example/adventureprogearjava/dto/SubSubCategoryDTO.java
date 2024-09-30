package com.example.adventureprogearjava.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubSubCategoryDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @Schema(description = "Subsubcategory name in Ukrainian", example = "Супер Толстовка")
    @NotBlank
    String subSubCategoryNameUa;

    @Schema(description = "Subsubcategory name in English", example = "Super Jumper")
    @NotBlank
    String subSubCategoryNameEn;

    @Schema(description = "ID of the parent subcategory", example = "3")
    Long subCategoryId;

    @Schema(description = "Self link", example = "/subsubcategories/super-jumper")
    String selfLink;
}
