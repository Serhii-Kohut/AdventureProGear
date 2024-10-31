package com.example.adventureprogearjava.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubcategoryDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @NotBlank
    String subcategoryNameUa;

    @NotBlank
    String subcategoryNameEn;

    Long parentCategoryId;

    List<SubSubCategoryDTO> subSubCategories;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String selfLink;
}
