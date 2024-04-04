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
    @Column(name = "category_name")
    String categoryName;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
}
