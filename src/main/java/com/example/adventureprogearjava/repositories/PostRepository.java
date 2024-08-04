package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into post (id, user_id, title, content, image) " +
            "values (nextval('post_seq'), :user_id, :postTitle, :content, :imageUrl)",
            nativeQuery = true)
    void insertPost(@Param("user_id") Long user_id,
                    @Param("postTitle") String postTitle,
                    @Param("content") String content,
                    @Param("imageUrl") String imageUrl);

    @Modifying
    @Query(value = "UPDATE post SET user_id = :user_id, title = :postTitle, content = :content, " +
            "image = :imageUrl " +
            "WHERE id = :id",
            nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("user_id") Long user_id,
                @Param("postTitle") String postTitle,
                @Param("content") String content,
                @Param("imageUrl") String imageUrl);

}
