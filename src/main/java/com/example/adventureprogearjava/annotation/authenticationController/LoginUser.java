package com.example.adventureprogearjava.annotation.authenticationController;

import com.example.adventureprogearjava.dto.authToken.AuthenticationResponseDto;
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
        summary = "User Login",
        description = "Endpoint for user login. Provides a token upon successful authentication.",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation.",
                        content = @Content(
                                schema = @Schema(implementation = AuthenticationResponseDto.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data provided.",
                        content = @Content(
                                schema = @Schema(type = "string")
                        )
                )
        }
)
public @interface LoginUser {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
