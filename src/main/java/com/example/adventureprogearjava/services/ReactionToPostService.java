package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.ReactionToPostDTO;
import com.example.adventureprogearjava.entity.enums.ReactionType;

import java.util.Map;

public interface ReactionToPostService {
    ReactionToPostDTO addReaction(Long postId, Long userId, ReactionType reactionType);

    Map<ReactionType, Long> countReaction(Long postId);

    void removeReaction(Long postId, Long userId);
}
