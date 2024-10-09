package com.example.adventureprogearjava.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "review_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewComment extends BaseEntity{
    @Transient
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_comment_seq")
    @SequenceGenerator(name = "review_comment_seq", sequenceName = "review_comment_seq", allocationSize = 1)
    Long sequenceId;

    @Column(name = "comment_text", nullable = false)
    String commentText;

    @Column(name = "comment_date", nullable = false)
    LocalDate commentDate;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private ProductReview productReview;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
