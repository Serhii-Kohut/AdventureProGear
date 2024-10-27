package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.ProductContent;
import com.example.adventureprogearjava.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO sections (id, sectioncaption_en, sectioncaption_ua, sectionicon) " +
            "VALUES (nextval('sections_seq'), :nameEn, :nameUa, :icon)", nativeQuery = true)
    void insertSection(@Param("nameEn") String nameEn,
                       @Param("nameUa") String nameUa,
                       @Param("icon") String icon);


    @Query(value = "SELECT lastval()", nativeQuery = true)
    Long getLastInsertedId();

    @Modifying
    @Query(value = "UPDATE sections SET sectioncaption_en = :nameEn, " +
            "sectioncaption_ua = :nameUa, sectionicon = :icon WHERE id = :id",
            nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("nameEn") String nameEn,
                @Param("nameUa") String nameUa,
                @Param("icon") String icon);


    Optional<Object> findBySectionCaptionUa(String sectionCaptionUa);

    Optional<Object> findBySectionCaptionEn(String sectionCaptionEn);
}