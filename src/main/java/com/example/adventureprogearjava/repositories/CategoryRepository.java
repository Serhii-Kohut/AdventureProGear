package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.Category;
import com.example.adventureprogearjava.entity.OrdersList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Modifying
    @Query(value = "insert into categories (id, category_name)\n" +
            "            values (nextval('categories_seq'), :name);",
            nativeQuery = true)
    void insertCategory(@Param("name") String name);

    @Modifying
    @Query(value = "UPDATE categories SET category_name = :name " +
            "WHERE id = :id",
            nativeQuery = true)
    void updateCategory(@Param("name") String name,
                           @Param("id") Long id);

    @Modifying
    @Query(value = "insert into categories (id, category_name, category_id)\n" +
            "            values (nextval('categories_seq'), :name, :categoy_id);",
            nativeQuery = true)
    void insertSubCategory(@Param("name") String name,
                           @Param("category_id") Long categoryId);


}
