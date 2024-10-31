package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO categories (id, category_name_en, category_name_ua) " +
            "VALUES (nextval('categories_seq'), :nameEn, :nameUa) RETURNING id;", nativeQuery = true)
    Long insertCategory(@Param("nameEn") String nameEn,
                        @Param("nameUa") String nameUa);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO categories (id, category_name_en, category_name_ua, section_id, parent_category_id) " +
            "VALUES (nextval('categories_seq'), :nameEn, :nameUa, :sectionId, null);", nativeQuery = true)
    void insertCategoryWithSection(@Param("nameEn") String nameEn,
                                   @Param("nameUa") String nameUa,
                                   @Param("sectionId") Long sectionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE categories SET category_name_en = :nameEn, category_name_ua = :nameUa " +
            "WHERE id = :id", nativeQuery = true)
    void updateCategory(@Param("nameEn") String nameEn,
                        @Param("nameUa") String nameUa,
                        @Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO categories (id, category_name_en, category_name_ua, parent_category_id, section_id) " +
            "VALUES (nextval('categories_seq'), :nameEn, :nameUa, :parentCategoryId, :sectionId);", nativeQuery = true)
    void insertSubCategory(@Param("nameEn") String nameEn,
                           @Param("nameUa") String nameUa,
                           @Param("parentCategoryId") Long parentCategoryId,
                           @Param("sectionId") Long sectionId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO categories (id, category_name_en, category_name_ua, parent_category_id, section_id) " +
            "VALUES (nextval('categories_seq'), :nameEn, :nameUa, :subCategoryId, :sectionId);", nativeQuery = true)
    void insertSubSubCategory(@Param("nameEn") String nameEn,
                              @Param("nameUa") String nameUa,
                              @Param("subCategoryId") Long subCategoryId,
                              @Param("sectionId") Long sectionId);

    @Query(value = "SELECT * FROM categories WHERE parent_category_id = :id", nativeQuery = true)
    List<Category> getAllSubCategories(@Param("id") Long id);

    @Query(value = "SELECT * FROM categories WHERE parent_category_id = :parentId", nativeQuery = true)
    List<Category> getAllSubSubCategories(@Param("parentId") Long parentId);

    @Query(value = "SELECT * FROM categories WHERE section_id = :id", nativeQuery = true)
    List<Category> getAllCategoriesBySection(@Param("id") Long id);

    Optional<Category> getCategoryByCategoryNameEnOrCategoryNameUa(String categoryNameEn, String categoryNameUa);

    Optional<Category> findByCategoryNameUa(String categoryNameUa);
}
