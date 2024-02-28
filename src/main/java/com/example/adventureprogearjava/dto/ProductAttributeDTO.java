package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttributeDTO {
    //    @NotNull(message = "Cannot add product attribute for not existent product")
//    Long productId;
    String size;
    String color;
    String additional;
    @NotNull
    Long priceDeviation;
    @JsonIgnore
    Long productId;
}
