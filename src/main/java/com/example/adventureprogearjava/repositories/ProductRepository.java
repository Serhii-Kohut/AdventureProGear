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
    @Modifying
    @Query(value = "insert into products (id ,product_name, description, base_price, gender, category)\n" +
            "            values (nextval('product_content_seq'), :name, :description, :price, CAST(:gender as gender) , CAST(:category as category));",
            nativeQuery = true)
    void insertProduct(@Param("name") String name,
                       @Param("description") String description,
                       @Param("price") Long price,
                       @Param("category") String category,
                       @Param("gender") String gender);

    @Modifying
    @Query(value = "UPDATE products set product_name = :name, " +
            "description=:description, base_price = :price," +
            "category = :category " +
            "where id = :id",
            nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("name") String name,
                @Param("description") String description,
                @Param("price") Long price,
                @Param("category") Long categoryId);

    @Modifying
    @Query(value = "UPDATE products set "+
            "gender= CAST(:gender as gender) where id = :id",
            nativeQuery = true)
    void updateGender(@Param("id") Long id,
                @Param("gender") String gender);

    @Query(value = "select * from products where starts_with(lower(product_name), lower(:name))",
            nativeQuery = true)
    List<Product> findByProductName(@Param("name") String name);

    @Query(value = "SELECT * FROM products where gender= CAST(:gender as gender)", nativeQuery = true)
    List<Product> findByGender(@Param("gender") String gender);

    @Query(value = "SELECT * FROM products p join categories c on c.id = p.category where c.category_name = :category", nativeQuery = true)
    List<Product> findByCategory(@Param("category") String category);

    @Query(value = "SELECT * FROM products p join categories c on c.id = p.category where c.category_name = :category and p.gender= CAST(:gender as gender)",
            nativeQuery = true)
    List<Product> findByCategoryAndGender(@Param("category") String category, @Param("gender") String gender);

    List<Product> findByBasePriceBetween(Long from, Long to);
}
