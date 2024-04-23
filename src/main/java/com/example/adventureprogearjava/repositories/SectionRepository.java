package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.ProductContent;
import com.example.adventureprogearjava.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    @Modifying
    @Query(value = "UPDATE sections set sectioncaption_en = :nameEn," +
            "sectioncaption_ua = :nameUa, sectionicon = :icon where id = :id",
            nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("nameEn") String nameEn,
                @Param("nameEn") String nameUa,
                @Param("icon") String icon);
}
