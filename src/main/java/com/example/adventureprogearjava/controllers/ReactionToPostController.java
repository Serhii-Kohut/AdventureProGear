package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.reactionToPostController.CreateReactionToPost;
import com.example.adventureprogearjava.annotation.reactionToPostController.DeleteReactionOfPost;
import com.example.adventureprogearjava.annotation.reactionToPostController.GetAllReactionsOfPost;
import com.example.adventureprogearjava.dto.ReactionToPostDTO;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.entity.enums.ReactionType;
import com.example.adventureprogearjava.services.impl.ReactionToPostServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/blog/reactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReactionToPostController {
    ReactionToPostServiceImpl reactionToPostService;

    @CreateReactionToPost(path = "/{postId}")
    public ReactionToPostDTO addReaction(@PathVariable Long postId, @RequestBody ReactionType reactionType, @AuthenticationPrincipal User user) {
        return reactionToPostService.addReaction(postId, user.getId(), reactionType);
    }

    @GetAllReactionsOfPost(path = "/{postId}/count")
    public Map<ReactionType, Long> countReactions(@PathVariable Long postId) {
        return reactionToPostService.countReaction(postId);
    }

    @DeleteReactionOfPost(path = "/{postId}")
    @DeleteMapping("/{postId}")
    public void removeReaction(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        reactionToPostService.removeReaction(postId, user.getId());
    }
}
