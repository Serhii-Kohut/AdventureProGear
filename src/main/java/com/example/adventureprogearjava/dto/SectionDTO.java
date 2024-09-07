package com.example.adventureprogearjava.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
    String sectionCaptionEn;

    String sectionCaptionUa;

    String sectionIcon;

    List<CategoryDTO> categories;

    String selfLink;

    String categoryCreationLink;
}
