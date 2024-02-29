package com.example.adventureprogearjava.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductStorageDTO {
    @NotNull
    @JsonIgnore
    Long productId;
    @NotNull
    @JsonIgnore
    Long attributeId;
    @NotNull
    @Min(value = 0, message = "Quantity cannot be negative or empty;")
    Integer quantity;
}
