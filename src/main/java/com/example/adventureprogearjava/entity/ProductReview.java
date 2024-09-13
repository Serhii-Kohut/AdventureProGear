package com.example.adventureprogearjava.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
    double rating;

    @Column(name = "comment")
    String comment;

    @Column(name = "review_date")
    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
