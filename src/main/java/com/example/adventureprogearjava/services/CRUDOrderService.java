package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.entity.User;

import java.util.List;

public interface CRUDOrderService {
    List<OrderDTO> getAll();
    List<OrderDTO> getAllOrdersByMe(User user);
    OrderDTO getOrderById(Long id, Long userId);

    OrderDTO createOrder(OrderDTO orderDTO, User user);

    void updateOrder(OrderDTO orderDTO, Long id, User user);

    void deleteOrder(Long id, User user);
}
