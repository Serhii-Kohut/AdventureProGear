package com.example.adventureprogearjava.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersListDTO {
    @NotNull
    Long orderId;

    @NotNull
    Long productId;

    Long productAttributeId;

    @NotNull
    @Min(0)
    Long quantity;
}
