package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.entity.OrdersList;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.services.CRUDService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class OrdersListServiceImplTest {
    @Autowired
    CRUDService<OrdersListDTO> orderListService;

    @Test
    void getAll() {
        List<OrdersListDTO> dtos = orderListService.getAll();
        assert (!dtos.isEmpty());
    }

    @Test
    void getById() {
        OrdersListDTO dtoById = orderListService.getById(1L);
        assert (dtoById != null);

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> orderListService.getById(9999L));
        assert (exception.getMessage().contains("Orders list not found with id"));
    }


}
