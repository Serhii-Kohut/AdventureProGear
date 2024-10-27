package com.example.adventureprogearjava.entity;

import jakarta.persistence.*;
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
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sections_seq")
    @SequenceGenerator(name = "sections_seq", sequenceName = "sections_seq", allocationSize = 1)
    private Long id;

    @Column(name = "sectioncaption_en", nullable = false)
    String sectionCaptionEn;
    @Column(name = "sectioncaption_ua", nullable = false)
    String sectionCaptionUa;
    @Column(name = "sectionicon")
    String sectionIcon;
    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Category> categories;

}
