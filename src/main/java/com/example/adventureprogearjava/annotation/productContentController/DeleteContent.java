package com.example.adventureprogearjava.annotation.productContentController;

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
        summary = "Delete product content by ID",
        description = "Deletes the product content identified by its ID. This operation removes the content from the system permanently.",
        security = @SecurityRequirement(name = "bearerAuth"),
        parameters = {
                @Parameter(
                        name = "id",
                        description = "ID of the content to be deleted. This is a mandatory path parameter.",
                        required = true,
                        schema = @Schema(type = "integer", format = "int64")
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Content successfully deleted."
                ),
                @ApiResponse(
                        responseCode = "204",
                        description = "No content present. The content was successfully deleted, but no content was returned.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Content not found with the provided ID.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized. The request lacks valid authentication credentials.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error. An unexpected error occurred on the server.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface DeleteContent {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
