package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.OrdersList;
import com.example.adventureprogearjava.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersListRepository extends JpaRepository<OrdersList, Long> {
    @Modifying
    @Query(value = "insert into orders_list (id, order_id, product_id, product_attribute_id, quantity) " +
            "values (nextval('order_list_seq'), :orderId, :productId, :productAttributeId, :quantity)",
            nativeQuery = true)
    void insertOrdersList(@Param("orderId") Long orderId,
                          @Param("productId") Long productId,
                          @Param("productAttributeId") Long productAttributeId,
                          @Param("quantity") Long quantity);

    @Modifying
    @Query(value = "UPDATE orders_list SET order_id = :orderId, product_id = :productId, product_attribute_id = :productAttributeId, quantity = :quantity " +
            "WHERE id = :id",
            nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("orderId") Long orderId,
                @Param("productId") Long productId,
                @Param("productAttributeId") Long productAttributeId,
                @Param("quantity") Long quantity);

    Optional<OrdersList> findByIdAndOrderUser(Long id, User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrdersList ol WHERE ol.order.id = :orderId")
    void deleteByOrderId(Long orderId);

}
