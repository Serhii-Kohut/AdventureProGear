package com.example.adventureprogearjava.annotation.productController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.DELETE)
@ResponseStatus(HttpStatus.OK)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Operation(
        summary = "Deleting product by its own id",
        description = "Deleting product by its own id",
        security = @SecurityRequirement(name = "bearerAuth")
)
@ApiResponse(
        responseCode = "200",
        description = "Successful operation."
)
@ApiResponse(
        responseCode = "403",
        description = "Access Denied",
        content = @Content(schema = @Schema(implementation = String.class))
)
@ApiResponse(
        responseCode = "204",
        description = "No content present.",
        content = @Content(schema = @Schema(implementation = String.class))
)
public @interface DeleteProduct {

    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
    @Parameter(
            description = "ID of the product",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "integer", format = "int64")
    )
    long id() default 0L;
}