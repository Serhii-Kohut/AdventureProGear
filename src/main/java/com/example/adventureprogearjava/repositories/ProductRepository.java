package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "insert into products (id, product_name_en, product_name_ua, description_en, description_ua, base_price, gender, category) " +
            "values (nextval('product_seq'), :nameEn, :nameUa, :descriptionEn, :descriptionUa, :price, CAST(:gender as gender), :categoryId) RETURNING id;",
            nativeQuery = true)
    Long insertProduct(@Param("nameEn") String nameEn,
                       @Param("nameUa") String nameUa,
                       @Param("descriptionEn") String descriptionEn,
                       @Param("descriptionUa") String descriptionUa,
                       @Param("price") Long price,
                       @Param("gender") String gender,
                       @Param("categoryId") Long categoryId);

    @Modifying
    @Query("UPDATE Product p SET p.averageRating = :averageRating WHERE p.id = :productId")
    void updateAverageRating(@Param("productId") Long productId, @Param("averageRating") Double averageRating);

    @Modifying
    @Query(value = "UPDATE products set product_name_en = :nameEn," +
            "product_name_ua = :nameUa, " +
            "description_en=:descriptionEn, description_ua=:descriptionUa, base_price = :price," +
            "category = :category " +
            "where id = :id",
            nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("nameEn") String nameEn,
                @Param("nameUa") String nameUa,
                @Param("descriptionEn") String descriptionEn,
                @Param("descriptionUa") String descriptionUa,

                @Param("price") Long price,
                @Param("category") Long categoryId);

    @Modifying
    @Query(value = "UPDATE products set " +
            "gender= CAST(:gender as gender) where id = :id",
            nativeQuery = true)
    void updateGender(@Param("id") Long id,
                      @Param("gender") String gender);

    @Query(value = "SELECT * FROM products " +
            "WHERE (lower(product_name_en) LIKE lower(concat('%', :name, '%')) " +
            "OR lower(product_name_ua) LIKE lower(concat('%', :name, '%')))",
            nativeQuery = true)
    List<Product> findByProductName(@Param("name") String name);


    @Query(value = "SELECT * FROM products where gender= CAST(:gender as gender)", nativeQuery = true)
    List<Product> findByGender(@Param("gender") String gender);

    @Query(value = "SELECT p.*, c.id as category_id, c.category_name_en " +
            "FROM products p " +
            "JOIN categories c ON c.id = p.category " +
            "WHERE c.category_name_en = :category",
            nativeQuery = true)
    List<Product> findByCategory(@Param("category") String category);

    @Query(value = "SELECT p.*, c.id as category_id " +
            "FROM products p " +
            "JOIN categories c ON c.id = p.category " +
            "WHERE c.category_name_en = :category " +
            "AND p.gender = CAST(:gender as gender)",
            nativeQuery = true)
    List<Product> findByCategoryAndGender(@Param("category") String category, @Param("gender") String gender);

    @Query("SELECT p.basePrice FROM Product p WHERE p.id = :productId")
    Long findProductPriceById(@Param("productId") Long productId);

    List<Product> findByBasePriceBetween(Long from, Long to);

    List<Product> findByBasePriceLessThanEqual(Long priceTo);

    List<Product> findByBasePriceGreaterThanEqual(Long priceFrom);

    @Query("SELECT p.descriptionEn FROM Product p WHERE p.id = :productId")
    String getProductNameById(@Param("productId") Long productId);

    @Query("SELECT DISTINCT p FROM Product p " +
            "JOIN FETCH p.category c " +
            "LEFT JOIN FETCH p.attributes " +
            "LEFT JOIN FETCH p.contents " +
            "LEFT JOIN FETCH p.productCharacteristics " +
            "LEFT JOIN FETCH c.parentCategory " +
            "LEFT JOIN FETCH c.section " +
            "LEFT JOIN FETCH c.characteristics " +
            "WHERE (:categoryId IS NULL OR c.id = :categoryId OR c.parentCategory.id = :categoryId) " +
            "AND (:priceFrom IS NULL OR p.basePrice >= :priceFrom) " +
            "AND (:priceTo IS NULL OR p.basePrice <= :priceTo) " +
            "AND (:gender IS NULL OR p.gender = :gender)")
    Page<Product> findByFilters(
            @Param("categoryId") Long categoryId,
            @Param("priceFrom") Long priceFrom,
            @Param("priceTo") Long priceTo,
            @Param("gender") String gender,
            Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p " +
            "JOIN p.category c " +
            "LEFT JOIN c.parentCategory pc " +
            "WHERE (:categoryId IS NULL OR c.id = :categoryId) " +
            "AND (:subcategoryId IS NULL OR c.id = :subcategoryId OR pc.id = :subcategoryId) " +
            "AND (:categoryName IS NULL OR c.categoryNameEn = :categoryName) " +
            "AND (:priceFrom IS NULL OR p.basePrice >= :priceFrom) " +
            "AND (:priceTo IS NULL OR p.basePrice <= :priceTo) " +
            "AND (:gender IS NULL OR p.gender = :gender)")
    Page<Product> findByAdvancedFilters(
            @Param("categoryId") Long categoryId,
            @Param("subcategoryId") Long subcategoryId,
            @Param("priceFrom") Long priceFrom,
            @Param("priceTo") Long priceTo,
            @Param("gender") String gender,
            @Param("categoryName") String categoryName,
            Pageable pageable);


    @Query("SELECT p FROM Product p WHERE p.category.categoryNameEn = :categoryName")
    Page<Product> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);


    @Query(value = "SELECT * FROM products WHERE " +
            "(LOWER(product_name_en) = LOWER(:name) OR LOWER(product_name_ua) = LOWER(:name))",
            nativeQuery = true)
    Optional<Product> findByNameEnOrNameUa(@Param("name") String name);

    @Modifying
    @Query("UPDATE Product p SET p.reviewCount = :reviewCount WHERE p.id = :productId")
    void updateReviewCount(@Param("productId") Long productId, @Param("reviewCount") int reviewCount);

}
