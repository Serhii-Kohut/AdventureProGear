package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.entity.User;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface CRUDOrderListService {
    List<OrdersListDTO> getAll();

    OrdersListDTO getById(Long id);

    OrdersListDTO create(OrdersListDTO ordersListDTO, User user) throws AccessDeniedException;

    void update(OrdersListDTO ordersListDTO, Long id, User user) throws AccessDeniedException;

    void delete(Long id, User user);

}
