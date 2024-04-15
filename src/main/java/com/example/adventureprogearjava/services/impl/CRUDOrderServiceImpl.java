package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.entity.Order;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.NoOrdersFoundException;
import com.example.adventureprogearjava.exceptions.NoUsersFoundException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.OrderMapper;
import com.example.adventureprogearjava.repositories.OrderRepository;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.CRUDOrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CRUDOrderServiceImpl implements CRUDOrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    OrderMapper orderMapper = OrderMapper.MAPPER;

    @Override
    public List<OrderDTO> getAll() {
        log.info("Getting all orders");

        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new NoOrdersFoundException("No orders found in the database");
        }

        return orders.stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> getAllOrdersByMe(User user) {
        List<Order> orders = orderRepository.findAllByUser(user);
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id, Long userId) {
        log.info("Getting order by id: {}", id);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoUsersFoundException("User not found with id " + userId));

        Order order = orderRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> {
                    log.warn("Order not found with id: {}", id);
                    return new ResourceNotFoundException("Order not found with id " + id);
                });

        return orderMapper.toDTO(order);
    }

    /*@Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        log.info("Creating new order.");

        if (orderDTO == null) {
            throw new IllegalArgumentException("OrderDTO cannot be null");
        }

        Order order = orderMapper.toEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDTO(savedOrder);
    }*/

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO, User user) {
        log.info("Creating new order.");
        insertOrder(orderDTO);
        return orderDTO;
    }

/*    @Override
    @Transactional
    public void update(OrderDTO orderDTO, Long id) {
        log.info("Updating order with id: {}", id);

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Order not found with id: {}", id);
                    return new ResourceNotFoundException("Order not found with id " + id);
                });

        Order orderToUpdate = orderMapper.toEntity(orderDTO);

        orderToUpdate.setId(existingOrder.getId());

        orderRepository.save(orderToUpdate);

    }*/

    @Override
    @Transactional
    public void updateOrder(OrderDTO orderDTO, Long id, User user) {
        log.info("Updating order with id: {}", id);
        if (!orderRepository.existsById(id)) {
            log.warn("Order not found!");
            throw new ResourceNotFoundException("Order not found with id " + id);
        } else {
            orderRepository.update(id, orderDTO.getCity(), orderDTO.getComment(), orderDTO.getOrderDate(),
                    orderDTO.getPostAddress(), orderDTO.getPrice(), orderDTO.getStatus().toString(), orderDTO.getUserId());
        }
    }


    @Override
    public void deleteOrder(Long id, User user) {
        log.info("Deleting order with id: {}", id);

        Order order = orderRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> {
                    log.warn("Order not found with id: {}", id);
                    return new ResourceNotFoundException("Order not found with id " + id);
                });

        orderRepository.delete(order);
    }

    private void insertOrder(OrderDTO orderDTO) {
        orderRepository.insertOrder(
                orderDTO.getCity(),
                orderDTO.getComment(),
                orderDTO.getOrderDate(),
                orderDTO.getPostAddress(),
                orderDTO.getPrice(),
                orderDTO.getStatus().toString(),
                orderDTO.getUserId()
        );
    }
}
