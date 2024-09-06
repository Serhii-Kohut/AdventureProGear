package com.example.adventureprogearjava.annotation.passwordResetController;

import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetRequestDTO;
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
@RequestMapping(method = RequestMethod.POST)
@Operation(
        summary = "Request password reset",
        description = "This endpoint allows users to request a password reset by providing their email address. The request should include the user's email to initiate the password reset process.",
        security = @SecurityRequirement(name = "bearerAuth"),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation.",
                        content = @Content(schema = @Schema(implementation = PasswordResetRequestDTO.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),

                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface RequestPassword {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
