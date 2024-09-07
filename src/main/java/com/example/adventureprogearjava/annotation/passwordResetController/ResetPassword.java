package com.example.adventureprogearjava.annotation.passwordResetController;

import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
        summary = "Reset password",
        description = "This endpoint allows users to reset their password. Users must provide their token and new password details. The request must include a valid token for verification, and both new password and confirmation must match.",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Password reset successful. The password has been updated.",
                        content = @Content(schema = @Schema(implementation = PasswordResetDTO.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data. The request data does not meet validation requirements. This can include issues with token, new password, or password confirmation.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized. The token provided is invalid or has expired.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found. The resource requested does not exist or the endpoint is incorrect.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error. An unexpected error occurred while processing the request.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface ResetPassword {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
