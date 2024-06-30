package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.Post;
import com.example.adventureprogearjava.entity.ReactionToPost;
import com.example.adventureprogearjava.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionToPostRepository extends JpaRepository<ReactionToPost, Long> {
    Optional<ReactionToPost> findByPostAndUser(Post post, User user);

    List<ReactionToPost> findByPost(Post post);
}
