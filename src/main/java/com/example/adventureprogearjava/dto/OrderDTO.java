package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.enums.OrderStatus;
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

    @NotNull
    Long userId;

    @NotNull
    LocalDateTime orderDate;

    @NotBlank
    String city;

    @NotBlank
    String postAddress;

    String comment;

    @NotNull
    @Min(0)
    Long price;

    @NotNull
    OrderStatus status;

    List<OrdersListDTO> ordersLists;

    String selfLink;

}
