package com.example.adventureprogearjava.dto;


import com.example.adventureprogearjava.entity.enums.ReactionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactionResponseDTO {
    Long postId;
    Long userId;
    String operation;
    ReactionType reactionType;
}
