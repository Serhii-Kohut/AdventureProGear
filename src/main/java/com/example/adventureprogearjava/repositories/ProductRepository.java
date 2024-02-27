package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.entity.enums.Gender;
import com.example.adventureprogearjava.entity.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    @Modifying
    @Query(value = "insert into products (id ,product_name, description, base_price, gender, category)\n" +
                    "            values (nextval('product_content_seq'), :name, :description, :price, CAST(:gender as gender) , CAST(:category as category));",
            nativeQuery = true)
    void insertProduct(@Param("name") String name,
                       @Param("description") String description,
                       @Param("price") Long price,
                       @Param("category") String category,
                       @Param("gender") String gender);
}
