package com.example.adventureprogearjava.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDTO {
    Long id;

    Long user_id;

    @NotBlank
    String postTitle;

    @NotBlank
    String content;

    @Column(name = "image")
    String imageUrl;


}
