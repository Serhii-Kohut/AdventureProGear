package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.Category;
import com.example.adventureprogearjava.entity.OrdersList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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
            "            values (nextval('categories_seq'), :name, :category_id);",
            nativeQuery = true)
    void insertSubCategory(@Param("name") String name,
                           @Param("category_id") Long categoryId);

    @Query(value = "select c.id,c.category_name, c.category_id from categories " +
            "join categories c on c.category_id = categories.id " +
            "where categories.id = :id", nativeQuery = true)
    List<Category> getAllSubCategories(@Param("id") Long id);

    Optional<Category> getCategoryByCategoryName(String name);
}
