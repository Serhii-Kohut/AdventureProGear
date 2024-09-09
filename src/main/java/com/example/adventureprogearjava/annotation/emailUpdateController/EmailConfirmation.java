package com.example.adventureprogearjava.annotation.emailUpdateController;

import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
        summary = "Confirm new email address.",
        description = "Confirms a new email address for a user using a token sent to the user's email. This endpoint is publicly accessible and does not require authentication.",
        security = @SecurityRequirement(name = "bearerAuth"),
        parameters = {
                @Parameter(
                        name = "token",
                        description = "The verification token sent to the user's new email address.",
                        required = true,
                        example = "abcdef1234567890"
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Email confirmed successfully.",
                        content = @Content(schema = @Schema(implementation = VerificationTokenMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid token or token expired.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "User not found or token does not exist.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface EmailConfirmation {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
