package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.PostDTO;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.PostNotFoundException;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long id) throws PostNotFoundException;

    PostDTO addNewPost(PostDTO postDTO, User user) throws PostNotFoundException;

    void updatePost(Long postId, PostDTO postDTO) throws PostNotFoundException;

    void deletePostById(Long postId) throws PostNotFoundException;
}
