package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.PostDTO;
import com.example.adventureprogearjava.exceptions.PostNotFoundException;
import com.example.adventureprogearjava.services.impl.PostServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/blog/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostServiceImpl postService;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable("postId") Long id) throws PostNotFoundException {
        return postService.getPostById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void addNewPost(@NotNull @Valid PostDTO postDTO) throws PostNotFoundException {
        postService.addNewPost(postDTO);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updatePost(@PathVariable("postId") Long postId, @RequestBody PostDTO postDTO) throws PostNotFoundException {
        postService.updatePost(postId, postDTO);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deletePost(@PathVariable("postId") Long postId) throws PostNotFoundException {
        postService.deletePostById(postId);
    }
}
