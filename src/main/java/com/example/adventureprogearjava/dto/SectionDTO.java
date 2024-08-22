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
    @Schema(example = "Clothing", description = "Назва розділу англійською мовою")
    String sectionCaptionEn;

    @Schema(example = "Одяг", description = "Назва розділу українською мовою")
    String sectionCaptionUa;

    @Schema(example = "icon7", description = "Іконка для розділу")
    String sectionIcon;

    @Schema(example = """
    [
        {
            "categoryNameUa": "Футболки",
            "categoryNameEn": "T-shirts",
            "subcategories": [
                {
                    "categoryNameUa": "Subcategory UA",
                    "categoryNameEn": "Subcategory EN",
                    "selfLink": "subcategorySelfLink"
                }
            ],
            "selfLink": "categorySelfLink"
        }
    ]
    """, description = "Список категорій, пов'язаних з цим розділом")
    List<CategoryDTO> categories;

    @Schema(example = "exampleSelfLink", description = "Посилання на розділ")
    String selfLink;

    @Schema(example = "exampleCreationLink", description = "Посилання для створення категорії в цьому розділі")
    String categoryCreationLink;
}
