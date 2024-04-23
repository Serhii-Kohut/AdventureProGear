package com.example.adventureprogearjava.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    Long id;
    @NotBlank
    String categoryNameUa;
    @NotBlank
    String categoryNameEn;
    List<CategoryDTO> subcategories;
    String selfLink;
}
