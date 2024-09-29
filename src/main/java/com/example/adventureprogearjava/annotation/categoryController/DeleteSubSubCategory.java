package com.example.adventureprogearjava.annotation.categoryController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
        summary = "Delete sub-subcategory",
        description = "Delete a sub-subcategory by its ID",
        security = @SecurityRequirement(name = "bearerAuth"),
        parameters = {
                @Parameter(
                        name = "id",
                        description = "ID of the sub-subcategory to delete",
                        required = true
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Successful deletion"
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Sub-subcategory Not Found",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface DeleteSubSubCategory {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
