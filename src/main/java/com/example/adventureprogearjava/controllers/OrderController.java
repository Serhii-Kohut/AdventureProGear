package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.orderController.*;
import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.dto.OrderUpdateDTO;
import com.example.adventureprogearjava.dto.UpdateOrderStatusDTO;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.services.impl.CRUDOrderServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Order Controller",
        description = "API operations with orders")
public class OrderController {
    CRUDOrderServiceImpl orderService;

    @GetAllOrders(path = "")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAll();
    }

    @GetMyOrders(path = "/me")
    public List<OrderDTO> getAllOrdersByMe(@AuthenticationPrincipal User user) {
        return orderService.getAllOrdersByMe(user);
    }

    @GetOrderById(path = "/{id}")
    public OrderDTO getOrderById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return orderService.getOrderById(id, user.getId());
    }

    @CreateOrder(path = "")
    public OrderDTO createOrder(@Valid @RequestBody OrderDTO orderDTO, @AuthenticationPrincipal User user) {
        return orderService.createOrder(orderDTO, user);
    }

    @UpdateOrder(path = "/{id}")
    public OrderDTO updateOrder(@Valid @RequestBody OrderUpdateDTO orderDTO, @PathVariable Long id, @AuthenticationPrincipal User user) {
        return orderService.updateOrder(orderDTO, id, user);
    }

    @UpdateStatus(path = "/status")
    public ResponseEntity<String> updateOrderStatus(@Valid @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        orderService.updateOrderStatus(updateOrderStatusDTO);
        return ResponseEntity.ok("Order status updated.");
    }

    @DeleteOrder(path = "/{id}")
    public void deleteOrder(@PathVariable Long id, @AuthenticationPrincipal User user) {
        orderService.deleteOrder(id, user);
    }
}
