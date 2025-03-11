package com.example.adventureprogearjava.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDTO {
    Long id;

    Long user_id;

    @NotBlank
    String titleEn;

    @NotBlank
    String titleUa;

    @NotBlank
    String contentEn;

    @NotBlank
    String contentUa;

    @Column(name = "image")
    String imageUrl;

    LocalDateTime createdAt;

}
