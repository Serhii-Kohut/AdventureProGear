package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.OrdersList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersListRepository extends JpaRepository<OrdersList, Long> {
    @Modifying
    @Query(value = "insert into orders_list (id, order_id, product_id, product_attribute_id, quantity) " +
            "values (nextval('orders_list_seq'), :orderId, :productId, :productAttributeId, :quantity)",
            nativeQuery = true)
    void insertOrdersList(@Param("orderId") Long orderId,
                          @Param("productId") Long productId,
                          @Param("productAttributeId") Long productAttributeId,
                          @Param("quantity") Long quantity);

}
