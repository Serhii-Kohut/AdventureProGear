package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    String api = "https://authentic-laughter-production.up.railway.app/api/orders/";

    @Mapping(target = "selfLink", source = "order.id", qualifiedByName = "idToLink")
    @Mapping(target = "userId", source = "order.user.id")
    OrderDTO toDTO(Order order);

    @Mapping(target = "status", source = "orderDTO.status")
    Order toEntity(OrderDTO orderDTO);

    @Named("idToLink")
    default String getLink(Long id) {
        return api + id;
    }
}
