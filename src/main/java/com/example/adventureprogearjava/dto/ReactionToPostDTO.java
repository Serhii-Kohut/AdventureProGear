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
public class ReactionToPostDTO {
    ReactionType reactionType;
    Long postId;
    Long userId;
}
