package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {

    Optional<ReviewComment> findById(Long commentId);

    List<ReviewComment> findByProductReviewId(Long reviewId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReviewComment rc WHERE rc.id = :id")
    void deleteById(@Param("id") Long id);
}
