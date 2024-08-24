package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ReactionToPostDTO;
import com.example.adventureprogearjava.entity.Post;
import com.example.adventureprogearjava.entity.ReactionToPost;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.entity.enums.ReactionType;
import com.example.adventureprogearjava.exceptions.InvalidReactionTypeException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.repositories.PostRepository;
import com.example.adventureprogearjava.repositories.ReactionToPostRepository;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.ReactionToPostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReactionToPostServiceImpl implements ReactionToPostService {
    ReactionToPostRepository reactionToPostRepository;
    UserRepository userRepository;
    PostRepository postRepository;

    @Override
    @Transactional
    public ReactionToPostDTO addReaction(Long postId, Long userId, ReactionType reactionType) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post not found"));

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        if (!isValidReactionType(reactionType)) {
            throw new InvalidReactionTypeException("Invalid reaction type: " + reactionType);
        }

        ReactionToPost existingReaction = reactionToPostRepository.findByPostAndUser(post, user)
                .orElse(null);

        if (existingReaction != null) {
            if (existingReaction.getReactionType().equals(reactionType)) {
                log.info("Reaction already exists on post {} for user {} with the same type", postId, userId);
                return new ReactionToPostDTO(reactionType, postId, userId);
            } else {
                reactionToPostRepository.delete(existingReaction);
                insertReaction(new ReactionToPostDTO(reactionType, postId, userId));
                log.info("Updated reaction on post {} for user {} ", postId, userId);
            }
        } else {
            insertReaction(new ReactionToPostDTO(reactionType, postId, userId));
            log.info("Inserted new reaction on post {} for user {} ", postId, userId);
        }

        return new ReactionToPostDTO(reactionType, postId, userId);
    }


    @Override
    @Transactional(readOnly = true)
    public Map<ReactionType, Long> countReaction(Long postId) {
        log.info("Counting reactions for post {}", postId);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        List<ReactionToPost> reactions = reactionToPostRepository.findByPost(post);

        Map<ReactionType, Long> reactionsCounts = reactions.stream()
                .collect(Collectors.groupingBy(ReactionToPost::getReactionType, Collectors.counting()));

        for (ReactionType type : ReactionType.values()) {
            reactionsCounts.putIfAbsent(type, 0L);
        }

        log.info("Counted {} reactions for post {}", reactionsCounts.size(), postId);

        return reactionsCounts;
    }

    @Transactional
    @Override
    public void removeReaction(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post not found"));

        User user = userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException("User not found"));

        Optional<ReactionToPost> optionalExistingReaction = reactionToPostRepository.findByPostAndUser(post, user);

        if (optionalExistingReaction.isPresent()) {
            reactionToPostRepository.delete(optionalExistingReaction.get());
            log.info("Deleted reaction on post {} for user {} ", postId, userId);
        } else {
            log.info("No reaction found on post {} for user {} ", postId, userId);
        }
    }

    private void insertReaction(ReactionToPostDTO reactionToPostDTO) {
        reactionToPostRepository.insertReaction(
                reactionToPostDTO.getReactionType().toString(),
                reactionToPostDTO.getPostId(),
                reactionToPostDTO.getUserId()
        );
    }

/*    private void updateReaction(ReactionToPostDTO reactionToPostDTO) {
        reactionToPostRepository.updateReaction(
                reactionToPostDTO.getReactionType().toString(),
                reactionToPostDTO.getPostId(),
                reactionToPostDTO.getUserId()
        );
    }*/

    private boolean isValidReactionType(ReactionType reactionType) {
        return Arrays.asList(ReactionType.values()).contains(reactionType);
    }

}
