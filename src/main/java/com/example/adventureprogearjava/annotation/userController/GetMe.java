package com.example.adventureprogearjava.annotation.userController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping(method = RequestMethod.GET)
@Operation(
        summary = "Get info about me",
        description = "Retrieves info about me",
        security = @SecurityRequirement(name = "bearerAuth"),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = @Content(schema = @Schema(implementation = com.example.adventureprogearjava.dto.UserDTO.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized - Authentication is required"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error - An unexpected error occurred"
                )
        }
)
public @interface GetMe {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}