package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.ProductContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductContentRepository extends JpaRepository<ProductContent, Long> {
    @Modifying
    @Query(value = "insert into product_content (id, source, product_id)\n" +
            "            values (nextval('product_content_seq'), :source, :product_id);",
            nativeQuery = true)
    void insertContent(@Param("source") String source,
                       @Param("product_id") Long productId);
    @Modifying
    @Query(value = "DELETE FROM product_content WHERE id =:id",
            nativeQuery = true)
    void deleteById(@Param("id") Long id);
}
