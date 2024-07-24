package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ReactionToPostDTO;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.entity.enums.ReactionType;
import com.example.adventureprogearjava.services.impl.ReactionToPostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Creating or updating Reaction of post.",
            description = "Creating or updating Reaction of post.")
    @ApiResponse(
            responseCode = "201",
            description = "Reaction created.",
            content = @Content(schema = @Schema(implementation = ReactionToPostDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public ReactionToPostDTO addReaction(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Reaction.",
            required = true,
            content = @Content(schema = @Schema(implementation = ReactionType.class)))
                                         @PathVariable Long postId, @RequestBody ReactionType reactionType,
                                         @AuthenticationPrincipal User user) {
        return reactionToPostService.addReaction(postId, user.getId(), reactionType);
    }

    @GetMapping("/{postId}/count")
    @Operation(summary = "Counting all reactions of post.",
            description = "Counting all reactions of post.")
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = Map.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Post not found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public Map<ReactionType, Long> countReactions(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "ID of Post.",
            required = true) @PathVariable Long postId) {
        return reactionToPostService.countReaction(postId);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Deleting Reaction of post.",
            description = "Deleting Reaction of post."
    )
    @ApiResponse(
            responseCode = "204",
            description = "No content present.",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public void removeReaction(@Parameter(
            description = "ID of Post.",
            required = true
    ) @PathVariable Long postId, @AuthenticationPrincipal User user) {
        reactionToPostService.removeReaction(postId, user.getId());
    }
}
