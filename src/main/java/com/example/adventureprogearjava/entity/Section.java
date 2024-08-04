package com.example.adventureprogearjava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "sections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Section extends BaseEntity{
    @Column(name = "sectioncaption_en", nullable = false)
    String sectionCaptionEn;
    @Column(name = "sectioncaption_ua", nullable = false)
    String sectionCaptionUa;
    @Column(name = "sectionicon")
    String sectionIcon;
    @OneToMany(mappedBy = "section")
    List<Category> categories;
}
