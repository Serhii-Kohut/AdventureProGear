package com.example.adventureprogearjava.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "products_review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductReview extends BaseEntity {
    @Transient
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_review_seq")
    @SequenceGenerator(name = "product_review_seq", sequenceName = "product_review_seq", allocationSize = 1)
    Long sequenceId;
    @Column(name = "user_name")
    String username;

    @Column
    double rating = 0.0;

    @Column(name = "comment")
    String comment;

    @Column(name = "review_date")
    LocalDate date;

    @Column(name = "likes")
    int likes;

    @Column(name = "dislikes")
    int dislikes;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "productReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductReviewReaction> reactions;

    @OneToMany(mappedBy = "productReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReviewComment> comments;

}
