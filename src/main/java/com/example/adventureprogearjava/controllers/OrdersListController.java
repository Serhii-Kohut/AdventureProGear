package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.orderController.*;
import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.services.impl.CRUDOrdersListServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-lists")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Order Controller",
        description = "API operations with order lists")
public class OrdersListController {

    CRUDOrdersListServiceImpl ordersListService;

    @GetAllOrderLists(path = "")
    public List<OrdersListDTO> getAllOrderLists() {
        return ordersListService.getAll();
    }

    @GetOrderListById(path = "/{id}")
    public OrdersListDTO getOrderListById(@PathVariable Long id) {
        return ordersListService.getById(id);
    }

    @CreateOrderLists(path = "")
    public OrdersListDTO createOrderList(@Valid @RequestBody OrdersListDTO ordersListDTO) {
        return ordersListService.create(ordersListDTO);
    }

    @UpdateOrderLists(path = "/{id}")
    public void updateOrderList(@Valid @RequestBody OrdersListDTO ordersListDTO, @PathVariable Long id) {
        ordersListService.update(ordersListDTO, id);
    }

    @DeleteOrderLists(path = "/{id}")
    public void deleteOrderList(@PathVariable Long id) {
        ordersListService.delete(id);
    }

}
