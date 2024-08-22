package com.example.adventureprogearjava.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentDTO {
    public ContentDTO(String source, Long productId) {
        this.source = source;
        this.productId = productId;
    }

    @JsonIgnore
    Long productId;
    @Schema(description = "Source URL of the content", example = "https://example.com/images/sneakers.png")
    @NotBlank
    String source;

    @Schema(description = "Self link", example = "/products/1/contents/1")
    String selfLink;
}
