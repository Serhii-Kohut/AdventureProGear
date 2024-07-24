package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.dto.PostDTO;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.PostNotFoundException;
import com.example.adventureprogearjava.services.impl.PostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blog/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostServiceImpl postService;

    @GetMapping
    @Operation(summary = "Get all posts",
            description = "Retrieves all available posts.")
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = PostDTO.class))
    )
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get post by ID",
            description = "Retrieves available post with ID.")
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = PostDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public PostDTO getPostById(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Post ID",
            required = true,
            content = @Content(schema = @Schema(implementation = PostDTO.class)))
                               @Parameter(description = "Post ID", required = true)
                               @PathVariable("postId") Long id) throws PostNotFoundException {
        return postService.getPostById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new post",
            description = "Creating new post by only user with role 'ADMIN'.")
    @ApiResponse(
            responseCode = "201",
            description = "Post created.",
            content = @Content(schema = @Schema(implementation = PostDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public PostDTO addNewPost(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "PostTitle, content, image URL",
            required = true,
            content = @Content(schema = @Schema(implementation = PostDTO.class)))
                              @Valid @RequestBody PostDTO postDTO, @AuthenticationPrincipal User user) {
        return postService.addNewPost(postDTO, user);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Update of the Post by only user with role 'ADMIN'.",
            description = "Update of the Post"
    )
    public void updatePost(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "PostTitle, content, image URL",
            required = true,
            content = @Content(schema = @Schema(implementation = PostDTO.class)))
                           @PathVariable("postId") Long postId, @RequestBody PostDTO postDTO, @AuthenticationPrincipal User user) {
        postService.updatePost(postId, postDTO, user);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = "Deleting Post by it's own id by only user with role 'ADMIN'.",
            description = "Deleting Post by it's own id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation."
    )
    @ApiResponse(
            responseCode = "204",
            description = "No content present.",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public void deletePost(@Parameter(
            description = "ID of the category",
            required = true)
                           @PathVariable("postId") Long postId) throws PostNotFoundException {
        postService.deletePostById(postId);
    }
}
