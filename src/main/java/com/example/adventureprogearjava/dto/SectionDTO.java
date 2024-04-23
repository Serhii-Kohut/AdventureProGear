package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.Category;
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
public class SectionDTO {
    @JsonIgnore
    Long id;
    @NotBlank(message = "Section name should be defined")
    String sectionCaptionEn;
    @NotBlank(message = "Section name should be defined")
    String sectionCaptionUa;
    String sectionIcon;
    List<CategoryDTO> categories;
    String selfLink;
}
