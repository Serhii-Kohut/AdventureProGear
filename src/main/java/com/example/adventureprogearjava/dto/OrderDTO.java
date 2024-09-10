package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long userId;

    LocalDateTime orderDate;

    @NotBlank
    String city;

    @NotBlank
    String postAddress;

    String comment;


    Long price;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    OrderStatus status;

    List<OrdersListDTO> ordersLists;

    String selfLink;

}
