package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.PostDTO;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.PostNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Page<PostDTO> getAllPosts(Pageable pageable);

    PostDTO getPostById(Long id) throws PostNotFoundException;

    PostDTO addNewPost(PostDTO postDTO, User user) throws PostNotFoundException;

    void updatePost(Long postId, PostDTO postDTO, User user) throws PostNotFoundException;

    void deletePostById(Long postId) throws PostNotFoundException;
}
