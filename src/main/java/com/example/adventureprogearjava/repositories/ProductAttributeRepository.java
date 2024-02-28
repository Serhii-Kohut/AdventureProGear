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
    @Query(value = "UPDATE product_attributes set size = :size, " +
            "color=:color, additional = :additional," +
            "price_deviation = :price_deviation where id=:id",
            nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("size") String size,
                @Param("additional") String additional,
                @Param("color") String color,
                @Param("price_deviation") Long price_deviation);
}
