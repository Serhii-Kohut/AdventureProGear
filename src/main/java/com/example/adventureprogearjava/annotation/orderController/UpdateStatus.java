package com.example.adventureprogearjava.annotation.orderController;

import com.example.adventureprogearjava.dto.UpdateOrderStatusDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.annotation.AliasFor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.PUT)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Operation(
        summary = "Update order status",
        description = "Update the status of an order",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
                description = "Order status data, required for updating",
                required = true,
                content = @Content(schema = @Schema(implementation = UpdateOrderStatusDTO.class))
        ),
        responses = {
                @ApiResponse
                        (responseCode = "200", description = "Order status updated successfully"),
                @ApiResponse
                        (responseCode = "400", description = "Invalid data", content = @Content(schema = @Schema(implementation = String.class))),
                @ApiResponse
                        (responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = String.class))),
                @ApiResponse
                        (responseCode = "403", description = "Forbidden: Insufficient permissions", content = @Content(schema = @Schema(implementation = String.class))),
                @ApiResponse
                        (responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
        }
)

public @interface UpdateStatus {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
