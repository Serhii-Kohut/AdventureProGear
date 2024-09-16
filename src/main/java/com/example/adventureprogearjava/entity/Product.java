package com.example.adventureprogearjava.entity;

import com.example.adventureprogearjava.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseEntity {

    @Transient
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    Long sequenceId;

    @Column(name = "product_name_ua", nullable = false)
    String productNameUa;

    @Column(name = "product_name_en", nullable = false)
    String productNameEn;

    @Column(name = "description_en", nullable = false)
    String descriptionEn;

    @Column(name = "description_ua", nullable = false)
    String descriptionUa;

    @Column(name = "base_price", nullable = false)
    Long basePrice;

    @Column(name = "average_rating")
    private Double averageRating = 0.0;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    Category category;

    @OneToMany(mappedBy = "product")
    List<OrdersList> ordersLists;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<ProductAttribute> attributes;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<ProductContent> contents;

}
