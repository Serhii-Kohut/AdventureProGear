package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ContentDTO;
import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.services.impl.CRUDOrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Order Controller",
        description = "API operations with orders")
public class OrderController {
    CRUDOrderServiceImpl orderService;

    @GetMapping
    @Operation(
            summary = "Get all orders",
            description = "Retrieves all available orders. Orders contains user information" +
                    "address for delivery, order status and price. "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = OrderDTO.class))
    )
    public List<OrderDTO> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get order by it's own id",
            description = "Retrieves order by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = OrderDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public OrderDTO getOrderById(@Parameter(
            description = "ID of the order",
            required = true
    ) @PathVariable Long id) {
        return orderService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "201",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = OrderDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Creation of new order",
            description = "Creation of new  order. "
    )
    public OrderDTO createOrder(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Order data, required for creation",
            required = true,
            content = @Content(schema = @Schema(implementation = OrderDTO.class))
    )
                                @Valid @RequestBody OrderDTO orderDTO) {
        return orderService.create(orderDTO);
    }

    @PutMapping("/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Update of the order",
            description = "Update of the order"
    )
    public void updateOrder(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Order data, required for creation",
            required = true,
            content = @Content(schema = @Schema(implementation = OrderDTO.class))
    )
                            @Valid @RequestBody OrderDTO orderDTO,
                            @Parameter(
                                    description = "ID of the order",
                                    required = true
                            ) @PathVariable Long id) {
        orderService.update(orderDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Deleting orders by it's own id",
            description = "Deleting orders by it's own id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation."
    )
    @ApiResponse(
            responseCode = "204",
            description = "No content present.",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public void deleteOrder(@Parameter(
            description = "ID of the order",
            required = true
    ) @PathVariable Long id) {
        orderService.delete(id);
    }

}
