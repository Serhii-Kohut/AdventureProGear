package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.entity.OrdersList;
import com.example.adventureprogearjava.exceptions.NoOrdersListFoundException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.OrdersListMapper;
import com.example.adventureprogearjava.repositories.OrdersListRepository;
import com.example.adventureprogearjava.services.CRUDService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrdersListServiceImpl implements CRUDService<OrdersListDTO> {
    OrdersListRepository ordersListRepository;
    OrdersListMapper ordersListMapper = OrdersListMapper.MAPPER;

    @Override
    public List<OrdersListDTO> getAll() {
        log.info("Getting all orders list");

        List<OrdersList> ordersLists = ordersListRepository.findAll();

        if (ordersLists.isEmpty()) {
            throw new NoOrdersListFoundException("No orders list found in the database");
        }

        return ordersLists.stream()
                .map(ordersListMapper::toDTO)
                .toList();
    }

    @Override
    public OrdersListDTO getById(Long id) {
        log.info("Getting orders list by id: {}", id);

        OrdersList ordersList = ordersListRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Orders list not found with id: {}", id);
                    return new ResourceNotFoundException("Orders list not found with id " + id);
                });

        return ordersListMapper.toDTO(ordersList);
    }

    @Override
    @Transactional
    public OrdersListDTO create(OrdersListDTO ordersListDTO) {
        log.info("Creating new orders list.");

        if (ordersListDTO == null) {
            throw new IllegalArgumentException("OrdersListDTO cannot be null");
        }

        OrdersList ordersList = ordersListMapper.toEntity(ordersListDTO);
        OrdersList savedOrdersList = ordersListRepository.save(ordersList);

        return ordersListMapper.toDTO(savedOrdersList);
    }

    @Override
    @Transactional
    public void update(OrdersListDTO ordersListDTO, Long id) {
        log.info("Updating orders list with id: {}", id);

        OrdersList existingOrdersList = ordersListRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Orders list not found with id: {}", id);
                    return new ResourceNotFoundException("Orders list not found with id " + id);
                });

        OrdersList ordersListToUpdate = ordersListMapper.toEntity(ordersListDTO);

        ordersListToUpdate.setId(existingOrdersList.getId());

        ordersListRepository.save(ordersListToUpdate);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting orders list with id: {}", id);

        ordersListRepository.deleteById(id);
    }
}
