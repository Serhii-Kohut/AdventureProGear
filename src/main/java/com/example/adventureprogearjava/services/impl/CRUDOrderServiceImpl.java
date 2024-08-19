package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.dto.UpdateOrderStatusDTO;
import com.example.adventureprogearjava.entity.Order;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.entity.enums.OrderStatus;
import com.example.adventureprogearjava.exceptions.NoOrdersFoundException;
import com.example.adventureprogearjava.exceptions.NoUsersFoundException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.OrderMapper;
import com.example.adventureprogearjava.repositories.OrderRepository;
import com.example.adventureprogearjava.repositories.ProductRepository;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.CRUDOrderService;
import com.example.adventureprogearjava.services.MailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final MailService mailService;
    private final ProductRepository productRepository;

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
    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO, User user) {
        log.info("Creating new order.");

        Order order = orderMapper.toEntity(orderDTO);
        order.setUser(user);

        if (orderDTO.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }
        if (orderDTO.getStatus() == null) {
            order.setStatus(OrderStatus.NEW);
        }

        Order savedOrder = orderRepository.save(order);
        orderDTO.setId(savedOrder.getId());
        orderDTO.setUserId(user.getId());

        sendOrderConfirmation(orderDTO, user.getId());
        return orderDTO;
    }

    private void sendOrderConfirmation(OrderDTO savedOrderDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));


        Order savedOrder = orderRepository.findById(savedOrderDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + savedOrderDTO.getId()));


        String subject = "Дякуємо за ваше замовлення!";
        StringBuilder message = new StringBuilder();
        message.append("Шановний(а) ").append(user.getName()).append(",\n\n")
                .append("Дякуємо за ваше замовлення №").append(savedOrder.getId())
                .append(" у нашому інтернет-магазині Adventure Pro Gear. Ваше замовлення отримано і буде оброблене найближчим часом.\n\n")
                .append("Ми зв'яжемося з вами, як тільки воно буде підтверджено. Ви можете слідкувати за статусом вашого замовлення у своєму особистому кабінеті на нашому сайті.\n\n")
                .append("З найкращими побажаннями,\n")
                .append("Команда Adventure Pro Gear\n\n");
        for (OrdersListDTO item : savedOrderDTO.getOrdersLists()) {
            String productName = productRepository.getProductNameById(item.getProductId());
            message.append("- Product: ").append(productName)
                    .append(", Quantity: ").append(item.getQuantity()).append("\n");
        }

        mailService.sendEmail(user.getEmail(), subject, message.toString());
    }

    @Transactional
    public void updateOrderStatus(UpdateOrderStatusDTO updateOrderStatusDTO) {
        Order order = orderRepository.findById(updateOrderStatusDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + updateOrderStatusDTO.getOrderId()));

        order.setStatus(updateOrderStatusDTO.getStatus());
        orderRepository.save(order);

        sendUpdateStatusNotification(order);

        log.info("Order status updated for order id: {} to status: {}", order.getId(), order.getStatus());
    }

    private void sendUpdateStatusNotification(Order order) {
        String subject = "";
        StringBuilder message = new StringBuilder();
        message.append("Шановний(а) ").append(order.getUser().getName()).append(",\n\n");

        switch (order.getStatus()) {
            case ACCEPTED:
                subject = "Ваше замовлення прийнято!";
                message.append("Ваше замовлення №").append(order.getId())
                        .append(" успішно прийняте і підтверджене. Наразі ми готуємо його до відправлення.\n\n")
                        .append("Дякуємо, що обрали наш магазин для своїх подорожей та пригод! Ми повідомимо вас, коли ваше замовлення буде відправлене.\n\n");
                break;
            case READY_TO_SHIP:
                subject = "Ваше замовлення готове до відправлення";
                message.append("Ваше замовлення №").append(order.getId())
                        .append(" готове до відправлення. Ми дбайливо пакуємо ваші товари і невдовзі передамо їх у службу доставки.\n\n")
                        .append("Ви отримаєте наступне повідомлення, коли ваше замовлення буде відправлено. Дякуємо, що обрали нас!\n\n");
                break;

            case SENT:
                subject = "Ваше замовлення вже у дорозі!";
                message.append("Раді повідомити, що ваше замовлення №").append(order.getId())
                        .append(" вже відправлено і прямує до вас!\n Ось номер для відстеження: 1902943.\n\n")
                        .append("Ви можете відстежувати своє замовлення на сайті служби доставки deliveryexampl.com. Очікуйте отримання вашого замовлення найближчим часом!\n\n");
                break;
            case DELIVERED:
                subject = "Ваше замовлення доставлено";
                message.append("Ваше замовлення №").append(order.getId())
                        .append(" успішно доставлено! Сподіваємося, що ви задоволені покупкою.\n\n")
                        .append("Ми будемо раді почути ваш відгук або побажання щодо товарів і сервісу. Дякуємо, що обрали наш магазин!\n\n");
                break;
            case CANCELED:
                subject = "Ваше замовлення скасовано!";
                message.append("На жаль, ваше замовлення №").append(order.getId())
                        .append(" було скасовано. Якщо у вас виникли питання, будь ласка, зв'яжіться з нашою службою підтримки.\n\n");
                break;

        }
        message.append("З повагою,\n" +
                "Команда Adventure Pro Gear");
        try {
            mailService.sendEmail(order.getUser().getEmail(), subject, message.toString());
            log.info("Email sent successfully for order id: {}", order.getId());
        } catch (Exception e) {
            log.error("Failed to send email for order id: {}", order.getId(), e);
        }

    }
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
}