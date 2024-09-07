package com.example.adventureprogearjava.annotation.registrationController;

import com.example.adventureprogearjava.dto.registrationDto.RegistrationDto;
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
        summary = "Registration of new user",
        description = "Registration of new user. Password must contain at least 8 characters, including at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=). The password should not contain any whitespace.",
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successful operation.",
                        content = @Content(schema = @Schema(implementation = RegistrationDto.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)


public @interface RegisterUser {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
