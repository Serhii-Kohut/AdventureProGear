package com.example.adventureprogearjava.annotation.userController;

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
@RequestMapping(method = RequestMethod.PUT)
@Operation(
        summary = "Update my email",
        description = "Updates the email of an existing user and sends a confirmation email",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
                description = "Email data required for updating the user's email",
                required = true,
                content = @Content(schema = @Schema(implementation = com.example.adventureprogearjava.dto.registrationDto.UserEmailDto.class))
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = @Content(schema = @Schema(implementation = com.example.adventureprogearjava.dto.registrationDto.UserEmailDto.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data",
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
public @interface UpdateMyEmail {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
