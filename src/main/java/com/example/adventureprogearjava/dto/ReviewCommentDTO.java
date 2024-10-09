package com.example.adventureprogearjava.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewCommentDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    String commentText;
    LocalDate commentDate;
    Long reviewId;
    Long userId;
}
