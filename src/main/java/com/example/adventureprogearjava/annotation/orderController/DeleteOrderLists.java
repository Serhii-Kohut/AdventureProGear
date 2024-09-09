package com.example.adventureprogearjava.annotation.orderController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping(method = RequestMethod.DELETE)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Operation(
        summary = "Delete order list by its ID",
        description = "Deletes an order list by the specified ID.",
        security = @SecurityRequirement(name = "bearerAuth"),
        parameters = {
                @Parameter(
                        name = "id",
                        description = "ID of the order list",
                        required = true,
                        schema = @Schema(type = "integer", format = "int64")
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "No content present.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Order list not found.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden: insufficient rights.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal server error.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface DeleteOrderLists {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
