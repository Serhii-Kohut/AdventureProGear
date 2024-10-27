package com.example.adventureprogearjava.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    String sectionCaptionEn;

    String sectionCaptionUa;

    String sectionIcon;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<CategoryDTO> categories;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String selfLink;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String categoryCreationLink;
}
