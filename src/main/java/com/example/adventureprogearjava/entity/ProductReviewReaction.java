package com.example.adventureprogearjava.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "review_reactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductReviewReaction extends BaseEntity {
    @Transient
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_review_reaction_seq")
    @SequenceGenerator(name = "product_review_reaction_seq", sequenceName = "product_review_reaction_seq", allocationSize = 1)
    Long sequenceId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "reaction_type")
    private String reactionType;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private ProductReview productReview;
}
