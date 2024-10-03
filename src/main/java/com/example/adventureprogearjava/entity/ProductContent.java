package com.example.adventureprogearjava.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "product_content")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductContent extends BaseEntity {

    @Transient
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_content_seq")
    @SequenceGenerator(name = "product_content_seq", sequenceName = "product_content_seq", allocationSize = 1)
    Long sequenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(name = "source", nullable = false)
    String source;

}
