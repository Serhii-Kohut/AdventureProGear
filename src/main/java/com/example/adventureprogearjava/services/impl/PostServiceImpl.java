package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.PostDTO;
import com.example.adventureprogearjava.entity.Post;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.PostNotFoundException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.PostMapper;
import com.example.adventureprogearjava.repositories.PostRepository;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImpl implements PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    PostMapper postMapper = PostMapper.MAPPER;


    @Override
    public List<PostDTO> getAllPosts() {
        log.info("Getting all posts.");
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(postMapper::postToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) throws PostNotFoundException {
        log.info("Get post by id: {}", id);
        return postMapper.postToDto(postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id " + id)));
    }

    @Override
    @Transactional
    public PostDTO addNewPost(PostDTO postDTO, User user) {
        log.info("Creating new post.");

        postDTO.setUser_id(user.getId());
        insertPost(postDTO);
        return postDTO;
    }


    @Override
    @Transactional
    public void updatePost(Long postId, PostDTO postDTO, User user) {
        log.info("Updating post with id: {}", postId);

        if (!postRepository.existsById(postId)) {
            log.warn("Post not found!");
            throw new ResourceNotFoundException("Post not found with id " + postId);
        } else {
            postRepository.update(postId, user.getId(), postDTO.getPostTitle(),
                    postDTO.getContent(), postDTO.getImageUrl());
        }

    }

    @Override
    public void deletePostById(Long postId) throws PostNotFoundException {
        log.info("Deleting post with id: {}", postId);
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException("Post not found with id " + postId);
        }
        postRepository.deleteById(postId);
    }

    private void insertPost(PostDTO postDTO) {
        postRepository.insertPost(
                postDTO.getUser_id(),
                postDTO.getPostTitle(),
                postDTO.getContent(),
                postDTO.getImageUrl()
        );
    }
}
