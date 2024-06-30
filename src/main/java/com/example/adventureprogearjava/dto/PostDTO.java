package com.example.adventureprogearjava.dto;

import jakarta.persistence.Column;
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
public class PostDTO {
    @NotNull
    Long id;

    @NotNull
    Long user_id;

    @NotNull
    String postTitle;

    @NotBlank
    String content;

    @Column(name = "image")
    String imageUrl;


}
