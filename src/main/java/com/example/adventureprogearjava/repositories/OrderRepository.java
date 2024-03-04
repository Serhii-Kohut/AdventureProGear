package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Query(value = "insert into orders (id, city, comment, order_date, post_address, price, status, user_id) " +
            "values (nextval('order_seq'), :city, :comment, :orderDate, :postAddress, :price, CAST(:status AS status), :userId)",
            nativeQuery = true)
    void insertOrder(@Param("city") String city,
                     @Param("comment") String comment,
                     @Param("orderDate") LocalDateTime orderDate,
                     @Param("postAddress") String postAddress,
                     @Param("price") Long price,
                     @Param("status") String status,
                     @Param("userId") Long userId);

    @Modifying
    @Query(value = "UPDATE orders SET city = :city, comment = :comment, order_date = :orderDate, " +
            "post_address = :postAddress, price = :price, status = CAST(:status AS status), user_id = :userId " +
            "WHERE id = :id",
            nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("city") String city,
                @Param("comment") String comment,
                @Param("orderDate") LocalDateTime orderDate,
                @Param("postAddress") String postAddress,
                @Param("price") Long price,
                @Param("status") String status,
                @Param("userId") Long userId);





}
