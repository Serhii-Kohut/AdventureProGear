package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.OrdersList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersListRepository extends JpaRepository<OrdersList, Long> {
}
