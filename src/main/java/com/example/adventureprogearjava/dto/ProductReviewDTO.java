package com.example.adventureprogearjava.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductReviewDTO {
    Long id;

    @NotNull(message = "Product ID is required")
    Long productId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String username;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    double rating;
    private int likes = 0;
    private int dislikes = 0;

    @NotBlank(message = "Comment is required")
    String comment;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date;
}
