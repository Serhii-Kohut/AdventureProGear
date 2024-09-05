package com.example.adventureprogearjava.annotation.userController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping(method = RequestMethod.DELETE)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Operation(
        summary = "Delete user by ID",
        description = "Deletes a user account by their ID",
        security = @SecurityRequirement(name = "bearerAuth"),
        parameters = @Parameter(
                name = "id",
                description = "ID of the user to be deleted",
                required = true,
                in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH
        ),
        responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "No content present - The user has been successfully deleted"
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized - Authentication is required"
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found - The user with the specified ID was not found"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error - An unexpected error occurred"
                )
        }
)
public @interface DeleteUser {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
