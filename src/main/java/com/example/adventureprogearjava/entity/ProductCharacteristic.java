package com.example.adventureprogearjava.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_characteristics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_characteristics_seq")
    @SequenceGenerator(name = "product_characteristics_seq", sequenceName = "product_characteristics_seq", allocationSize = 1)
    Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_characteristic_id", nullable = false)
    private CategoryCharacteristic categoryCharacteristic;
}
