package com.example.adventureprogearjava.annotation.productContentController;

import com.example.adventureprogearjava.dto.ContentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
        summary = "Update existing content",
        description = "Updates the content identified by its ID. The request body should include the updated content details such as image URL, format, and other related metadata.",
        security = @SecurityRequirement(name = "bearerAuth"),
        parameters = {
                @Parameter(
                        name = "id",
                        description = "ID of the content to be updated. This is a mandatory path parameter.",
                        required = true,
                        schema = @Schema(type = "integer", format = "int64")
                )
        },
        requestBody = @RequestBody(
                description = "Content data required for updating the content. This includes updated details such as image URL, format, and other related metadata.",
                required = true,
                content = @Content(schema = @Schema(implementation = ContentDTO.class))
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Content successfully updated.",
                        content = @Content(schema = @Schema(implementation = ContentDTO.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data provided. The request was invalid or cannot be otherwise served.",
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
public @interface UpdateContent {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
