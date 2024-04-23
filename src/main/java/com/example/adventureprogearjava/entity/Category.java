package com.example.adventureprogearjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "category_name_en")
    String categoryNameEn;
    @Column(name = "category_name_ua")
    String categoryNameUa;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    @ManyToOne
    @JoinColumn(name = "section_id")
    Section section;
}
