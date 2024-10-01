package com.example.adventureprogearjava.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_seq")
    @SequenceGenerator(name = "categories_seq", sequenceName = "categories_seq", allocationSize = 1)
    Long id;

    @Column(name = "category_name_en", nullable = false)
    String categoryNameEn;

    @Column(name = "category_name_ua", nullable = false)
    String categoryNameUa;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    Category parentCategory;

    @ManyToOne
    @JoinColumn(name = "section_id")
    Section section;

    @Transient
    public boolean isSubCategory() {
        return this.parentCategory != null;
    }
}
