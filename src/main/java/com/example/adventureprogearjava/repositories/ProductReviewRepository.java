package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findByProductId(Long productId);

    @Modifying
    @Query("UPDATE ProductReview pr SET pr.likes = pr.likes + 1 WHERE pr.id = :id")
    void incrementLikes(@Param("id") Long id);

    @Modifying
    @Query("UPDATE ProductReview pr SET pr.dislikes = pr.dislikes + 1 WHERE pr.id = :id")
    void incrementDislikes(@Param("id") Long id);

    @Modifying
    @Query("UPDATE ProductReview pr SET pr.likes = :likes WHERE pr.id = :id")
    void updateLikes(@Param("id") Long id, @Param("likes") int likes);

    @Modifying
    @Query("UPDATE ProductReview pr SET pr.dislikes = :dislikes WHERE pr.id = :id")
    void updateDislikes(@Param("id") Long id, @Param("dislikes") int dislikes);
}
