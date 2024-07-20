package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ReactionToPostDTO;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.entity.enums.ReactionType;
import com.example.adventureprogearjava.services.impl.ReactionToPostServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/blog/reactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReactionToPostController {
    ReactionToPostServiceImpl reactionToPostService;

    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReactionToPostDTO addReaction(@PathVariable Long postId,
                                         @RequestBody ReactionType reactionType,
                                         @AuthenticationPrincipal User user) {

        return reactionToPostService.addReaction(postId, user.getId(), reactionType);
    }

    @GetMapping("/{postId}/count")
    public Map<ReactionType, Long> countReactions(@PathVariable Long postId) {
        return reactionToPostService.countReaction(postId);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeReaction(@PathVariable Long postId,
                               @AuthenticationPrincipal User user) {

        reactionToPostService.removeReaction(postId, user.getId());
    }

}
