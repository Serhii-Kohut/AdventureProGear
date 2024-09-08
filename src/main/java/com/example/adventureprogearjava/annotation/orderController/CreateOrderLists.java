package com.example.adventureprogearjava.annotation.orderController;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.POST)
@Operation(
        summary = "Creation of new order list",
        description = "Creates a new order list.",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
                description = "Order list data, required for creation",
                required = true,
                content = @Content(schema = @Schema(implementation = OrdersListDTO.class))
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Order list successfully created.",
                        content = @Content(schema = @Schema(implementation = OrdersListDTO.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data provided.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden: Insufficient permissions",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal server error",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface CreateOrderLists {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
