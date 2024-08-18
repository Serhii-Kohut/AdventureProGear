package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class UpdateOrderStatusDTO {
    @Schema(description = "ID of the order", example = "5")
    @NotNull
    private Long orderId;

    @Schema(description = "Status of the order", example = "ACCEPTED")
    @NotNull
    private OrderStatus status;
}
