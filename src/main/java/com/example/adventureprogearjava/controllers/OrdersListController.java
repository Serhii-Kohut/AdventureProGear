package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.services.impl.CRUDOrdersListServiceImpl;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-lists")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Order Controller",
        description = "API operations with order lists")
public class OrdersListController {

    CRUDOrdersListServiceImpl ordersListService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = "Get all order lists",
            description = "Retrieves all available order lists. Order list contains info about " +
                    "products in order."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = OrdersListDTO.class))
    )
    public List<OrdersListDTO> getAllOrderLists() {
        return ordersListService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get order by it's own id",
            description = "Retrieves order list by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = OrdersListDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public OrdersListDTO getOrderListById(@Parameter(
            description = "ID of the order",
            required = true
    ) @PathVariable Long id) {
        return ordersListService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "201",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = OrdersListDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Creation of new order list",
            description = "Creation of new  order list."
    )
    public OrdersListDTO createOrderList(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Order list data, required for creation",
            required = true,
            content = @Content(schema = @Schema(implementation = OrdersListDTO.class))
    )
                                         @Valid @RequestBody OrdersListDTO ordersListDTO) {
        return ordersListService.create(ordersListDTO);
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
            summary = "Update of the order list",
            description = "Update of the order list"
    )
    public void updateOrderList(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Order list data, required for creation",
                    required = true,
                    content = @Content(schema = @Schema(implementation = OrdersListDTO.class))
            ) @Valid @RequestBody OrdersListDTO ordersListDTO,
            @PathVariable Long id) {
        ordersListService.update(ordersListDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Deleting order lists by it's own id",
            description = "Deleting order lists by it's own id"
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
    public void deleteOrderList(@PathVariable Long id) {
        ordersListService.delete(id);
    }

}
