package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.services.impl.CRUDOrdersListServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-lists")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrdersListController {

    CRUDOrdersListServiceImpl ordersListService;

    @GetMapping
    public List<OrdersListDTO> getAllOrderLists() {
        return ordersListService.getAll();
    }

    @GetMapping("/{id}")
    public OrdersListDTO getOrderListById(@PathVariable Long id) {
        return ordersListService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdersListDTO createOrderList(@Valid @RequestBody OrdersListDTO ordersListDTO) {
        return ordersListService.create(ordersListDTO);
    }

    @PutMapping("/{id}")
    public void updateOrderList(@Valid @RequestBody OrdersListDTO ordersListDTO, @PathVariable Long id) {
        ordersListService.update(ordersListDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderList(@PathVariable Long id) {
        ordersListService.delete(id);
    }

}
