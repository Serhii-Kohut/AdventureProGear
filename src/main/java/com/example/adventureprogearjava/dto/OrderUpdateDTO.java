package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.enums.OrderStatus;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateDTO {
    @Size(min = 1, message = "City cannot be empty")
    String city;
    String postAddress;
    Long price;
    String comment;
    LocalDateTime orderDate;
    OrderStatus status;
}
