package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.entity.Order;
import com.example.adventureprogearjava.entity.OrdersList;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.entity.enums.Role;
import com.example.adventureprogearjava.exceptions.AccessToOrderDeniedException;
import com.example.adventureprogearjava.exceptions.NoOrdersListFoundException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.OrdersListMapper;
import com.example.adventureprogearjava.repositories.OrderRepository;
import com.example.adventureprogearjava.repositories.OrdersListRepository;
import com.example.adventureprogearjava.services.CRUDOrderListService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CRUDOrdersListServiceImpl implements CRUDOrderListService {
    OrdersListRepository ordersListRepository;
    OrdersListMapper ordersListMapper;
    private final OrderRepository orderRepository;

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

/*    @Override
    @Transactional
    public OrdersListDTO create(OrdersListDTO ordersListDTO) {
        log.info("Creating new orders list.");

        if (ordersListDTO == null) {
            throw new IllegalArgumentException("OrdersListDTO cannot be null");
        }

        OrdersList ordersList = ordersListMapper.toEntity(ordersListDTO);
        OrdersList savedOrdersList = ordersListRepository.save(ordersList);

        return ordersListMapper.toDTO(savedOrdersList);
    }*/

    @Override
    @Transactional
    public OrdersListDTO create(OrdersListDTO ordersListDTO, User user) throws AccessDeniedException {
        log.info("Creating new orders list.");

        Order order = orderRepository.findById(ordersListDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID " + ordersListDTO.getOrderId()));

        if (!order.getUser().getId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new AccessToOrderDeniedException("You do not have permission to UPDATE order lists for this order.");
        }

        insertOrdersList(ordersListDTO);
        return ordersListDTO;
    }

    @Override
    @Transactional
    public void update(OrdersListDTO ordersListDTO, Long id, User user) throws AccessDeniedException {
        log.info("Updating orders list with id: {}", id);

        if (ordersListDTO.getOrderId() == null) {
            throw new IllegalArgumentException("orderId cannot be null");
        }

        Order order = orderRepository.findById(ordersListDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID " + ordersListDTO.getOrderId()));

        if (!order.getUser().getId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new AccessToOrderDeniedException("You do not have permission to UPDATE order lists for this order.");
        }

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
    public void delete(Long id, User user) {
        log.info("Deleting orders list with id: {}", id);

        OrdersList ordersList = ordersListRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("OrderList not found with id: {}", id);
                    return new ResourceNotFoundException("OrderList not found with id " + id);
                });

        Order order = ordersList.getOrder();

        if (!order.getUser().getId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new AccessToOrderDeniedException("You do not have permission to DELETE order lists for this order.");
        }

        ordersListRepository.delete(ordersList);
    }


    private void insertOrdersList(OrdersListDTO ordersListDTO) {
        ordersListRepository.insertOrdersList(
                ordersListDTO.getOrderId(),
                ordersListDTO.getProductId(),
                ordersListDTO.getProductAttributeId(),
                ordersListDTO.getQuantity()
        );
    }
}
