package com.example.adventureprogearjava.annotation.registrationController;

import com.example.adventureprogearjava.controllers.RegistrationController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.GET)
@Operation(
        summary = "Confirmation of user registration",
        description = "Confirmation of user registration",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation.",
                        content = @Content(schema = @Schema(implementation = RegistrationController.ApiResponse.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)

public @interface ConfirmationUserRegistration {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
