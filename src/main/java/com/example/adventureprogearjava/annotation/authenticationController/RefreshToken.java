package com.example.adventureprogearjava.annotation.authenticationController;

import com.example.adventureprogearjava.dto.authToken.RefreshTokenResponseDto;
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
        summary = "Refresh token of a user",
        description = "Endpoint to refresh the authentication token for a user. Requires a valid refresh token in the request body.",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation. Returns a new refresh token.",
                        content = @Content(
                                schema = @Schema(implementation = RefreshTokenResponseDto.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data provided in the request body.",
                        content = @Content(
                                schema = @Schema(type = "string")
                        )
                )
        }
)
public @interface RefreshToken {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
