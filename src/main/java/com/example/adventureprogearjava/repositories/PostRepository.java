package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into post (id, user_id, title_en, title_ua, content_en, content_ua, image, created_at) " +
            "values (nextval('post_seq'), :user_id, :titleEn, :titleUa, :contentEn, :contentUa, :imageUrl, now())",
            nativeQuery = true)
    void insertPost(@Param("user_id") Long user_id,
                    @Param("titleEn") String titleEn,
                    @Param("titleUa") String titleUa,
                    @Param("contentEn") String contentEn,
                    @Param("contentUa") String contentUa,
                    @Param("imageUrl") String imageUrl);

    @Modifying
    @Transactional
    @Query(value = "UPDATE post SET " +
            "user_id = COALESCE(:user_id, user_id), " +
            "title_en = COALESCE(:titleEn, title_en), " +
            "title_ua = COALESCE(:titleUa, title_ua), " +
            "content_en = COALESCE(:contentEn, content_en), " +
            "content_ua = COALESCE(:contentUa, content_ua), " +
            "image = COALESCE(:imageUrl, image) " +
            "WHERE id = :id",
            nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("user_id") Long user_id,
                @Param("titleEn") String titleEn,
                @Param("titleUa") String titleUa,
                @Param("contentEn") String contentEn,
                @Param("contentUa") String contentUa,
                @Param("imageUrl") String imageUrl);

    Optional<Post> findTopByAuthorIdOrderByCreatedAtDesc(Long userId);

}
