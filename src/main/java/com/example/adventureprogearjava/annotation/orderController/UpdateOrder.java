package com.example.adventureprogearjava.annotation.orderController;

import com.example.adventureprogearjava.dto.OrderUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping(method = RequestMethod.PUT)
@Operation(
        summary = "Update of the order",
        description = "Update of the order",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
                description = "Order data, required for creation",
                required = true,
                content = @Content(schema = @Schema(implementation = OrderUpdateDTO.class))
        ),
        parameters = {
                @Parameter(
                        name = "id",
                        description = "ID of the order",
                        required = true,
                        schema = @Schema(type = "long")
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation."
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface UpdateOrder {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
