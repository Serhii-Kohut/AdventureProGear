package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.entity.Order;
import com.example.adventureprogearjava.entity.OrdersList;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.entity.ProductAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrdersListMapper {
    String api = "https://empowering-happiness-production.up.railway.app/api/order-lists/";

    OrdersListMapper MAPPER = Mappers.getMapper(OrdersListMapper.class);

    @Mapping(target = "selfLink", source = "ordersList.id", qualifiedByName = "idToLink")
    @Mapping(target = "id", source = "ordersList.id")
    @Mapping(target = "orderId", source = "ordersList.order.id")
    @Mapping(target = "productId", source = "ordersList.product.id")
    @Mapping(target = "productAttributeId", source = "ordersList.productAttribute.id")
    OrdersListDTO toDTO(OrdersList ordersList);

    @Mapping(source = "orderId", target = "order", qualifiedByName = "orderIdToOrder")
    @Mapping(source = "productId", target = "product", qualifiedByName = "productIdToProduct")
    @Mapping(source = "productAttributeId", target = "productAttribute", qualifiedByName = "productAttributeIdToProductAttribute")
    OrdersList toEntity(OrdersListDTO ordersListDTO);

    @Named("orderIdToOrder")
    default Order orderIdToOrder(Long orderId) {
        if (orderId == null) {
            return null;
        }

        Order order = new Order();
        order.setId(orderId);
        return order;
    }

    @Named("productIdToProduct")
    default Product productIdToProduct(Long productId) {
        if (productId == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @Named("productAttributeIdToProductAttribute")
    default ProductAttribute productAttributeIdToProductAttribute(Long productAttributeId) {
        if (productAttributeId == null) {
            return null;
        }

        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(productAttributeId);
        return productAttribute;
    }

    @Named("idToLink")
    default String getLink(Long id) {
        return api + id;
    }
}
