package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.PostDTO;
import com.example.adventureprogearjava.entity.Post;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.PostNotFoundException;
import com.example.adventureprogearjava.mapper.PostMapper;
import com.example.adventureprogearjava.repositories.PostRepository;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImpl implements PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    PostMapper postMapper;


    @Override
    public PostDTO getPostById(Long id) throws PostNotFoundException {
        return postMapper.postToDto(postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id " + id)));
    }

    @Override
    public void addNewPost(PostDTO postDTO) throws PostNotFoundException {
        User author = userRepository.findById(postDTO.getUser_id())
                .orElseThrow(() -> new PostNotFoundException("User not found with id " + postDTO.getUser_id()));

        Post post = postMapper.postDtoToEntity(postDTO);
        post.setAuthor(author);
        postRepository.save(post);
    }

    @Override
    public void updatePost(Long postId, PostDTO postDTO) throws PostNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id " + postId));

        User author = userRepository.findById(postDTO.getUser_id())
                .orElseThrow(() -> new PostNotFoundException("User not found with id " + postDTO.getUser_id()));

        post.setPostTitle(postDTO.getPostTitle());
        post.setAuthor(author);
        post.setContent(postDTO.getContent());

        postRepository.save(post);

    }

    @Override
    public void deletePostById(Long postId) throws PostNotFoundException {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException("Post not found with id " + postId);
        }
        postRepository.deleteById(postId);
    }
}
