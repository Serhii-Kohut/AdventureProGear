package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttributeDTO {
    @NotNull(message = "Cannot add product attribute for not existent product")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Long productId;
    String size;
    String color;
    String additional;
    @NotNull
    Long priceDeviation;
    @NotNull
    @Min(0)
    Long quantity;
    String selfLink;
}
