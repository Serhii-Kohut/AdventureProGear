package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = OrdersListMapper.class)
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    String api = "https://adventure-jtvg.onrender.com/api/orders/";

    @Mapping(target = "selfLink", source = "order.id", qualifiedByName = "orderIdToLink")
    @Mapping(target = "userId", source = "order.user.id")
    @Mapping(target = "ordersLists", source = "order.ordersLists") // Мапінг колекції ordersLists через OrdersListMapper
    OrderDTO toDTO(Order order);

    @Mapping(target = "ordersLists", source = "ordersLists") // Для toEntity: мапінг ordersLists з DTO на сутність
    Order toEntity(OrderDTO orderDTO);

    // Додатковий метод для мапінгу ID на selfLink (якщо потрібно)
    @Named("orderIdToLink")
    default String orderIdToLink(Long orderId) {
        return api + orderId;
    }
}
