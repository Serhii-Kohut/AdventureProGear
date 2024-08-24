package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.Post;
import com.example.adventureprogearjava.entity.ReactionToPost;
import com.example.adventureprogearjava.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionToPostRepository extends JpaRepository<ReactionToPost, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO reactions (id, type, post_id, user_id) " +
            "VALUES (nextval('reaction_seq'), CAST(:reactionType AS reaction_type), :postId, :userId)",
            nativeQuery = true)
    void insertReaction(@Param("reactionType") String reactionType,
                        @Param("postId") Long postId,
                        @Param("userId") Long userId);

/*    @Modifying
    @Transactional
    @Query(value = "UPDATE reactions SET type = CAST(:reactionType AS reaction_type), post_id = :postId, " +
            "user_id = :userId", nativeQuery = true)
    void updateReaction(@Param("reactionType") String reactionType,
                        @Param("postId") Long postId,
                        @Param("userId") Long userId);*/


    Optional<ReactionToPost> findByPostAndUser(Post post, User user);

    List<ReactionToPost> findByPost(Post post);
}
