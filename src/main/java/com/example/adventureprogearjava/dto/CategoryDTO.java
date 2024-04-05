package com.example.adventureprogearjava.dto;

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
    @NotBlank
    String categoryName;
    List<CategoryDTO> subcategories;
    String selfLink;

}