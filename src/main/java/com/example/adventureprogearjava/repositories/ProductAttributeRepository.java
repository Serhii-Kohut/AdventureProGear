package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    @Modifying
    @Query(value = "insert into product_attributes (id, size, color, additional, price_deviation, product_id, quantity, label, picture_url)" +
            "            values (nextval('product_attr_seq'),:size,:color,:additional, :price_deviation, :product_id, :quantity, :label, :picture_url);",
            nativeQuery = true)
    void insertProductAttr(@Param("size") String size,
                           @Param("additional") String additional,
                           @Param("color") String color,
                           @Param("price_deviation") Long priceDeviation,
                           @Param("product_id") Long productId,
                           @Param("quantity") Long quantity,
                           @Param("label") String label,
                           @Param("picture_url") String pictureUrl);

    @Modifying
    @Query(value = "UPDATE product_attributes set size = :size, " +
            "color=:color, additional = :additional," +
            "price_deviation = :price_deviation, quantity = :quantity, label = :label, picture_url = :picture_url where id=:id",
            nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("size") String size,
                @Param("additional") String additional,
                @Param("color") String color,
                @Param("price_deviation") Long priceDeviation,
                @Param("quantity") Long quantity,
                @Param("label") String label,
                @Param("picture_url") String pictureUrl);

    @Modifying
    @Query(value = "DELETE FROM product_attributes WHERE id =:id",
            nativeQuery = true)
    void deleteById(@Param("id") Long id);
}
