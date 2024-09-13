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

    String api = "https://authentic-laughter-production.up.railway.app//api/orders/";

    @Mapping(target = "selfLink", source = "order.id", qualifiedByName = "orderIdToLink")
    @Mapping(target = "userId", source = "order.user.id")
    @Mapping(target = "ordersLists", source = "order.ordersLists") // Додайте мапінг для ordersLists
    OrderDTO toDTO(Order order);

    @Mapping(target = "status", source = "orderDTO.status")
    Order toEntity(OrderDTO orderDTO);

    @Named("orderIdToLink")
    default String getLink(Long id) {
        return api + id;
    }
}
