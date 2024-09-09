package com.example.adventureprogearjava.annotation.productContentController;

import com.example.adventureprogearjava.dto.ContentDTO;
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
@RequestMapping(method = RequestMethod.POST)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Operation(
        summary = "Create new content",
        description = "Creates new content. The content may include images in various formats such as jpg, png, jpeg, webp, etc. " +
                "The request body must include the content details required for creation.",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
                description = "Content data required for creation. This includes details such as image URL, format, and other related metadata.",
                required = true,
                content = @Content(schema = @Schema(implementation = ContentDTO.class))
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Content successfully created.",
                        content = @Content(schema = @Schema(implementation = ContentDTO.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data provided. The request was invalid or cannot be otherwise served.",
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
public @interface CreateContent {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
