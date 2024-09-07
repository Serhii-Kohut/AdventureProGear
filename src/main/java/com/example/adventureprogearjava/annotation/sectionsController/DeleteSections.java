package com.example.adventureprogearjava.annotation.sectionsController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
        summary = "Delete a section",
        description = "Deletes a section specified by the ID.",
        security = @SecurityRequirement(name = "bearerAuth"),
        parameters = @Parameter(
                name = "id",
                description = "ID of the section to be deleted",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer", format = "int64")
        ),
        responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Section deleted successfully.",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(type = "string")
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Section not found.",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(type = "string")
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Server error occurred.",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(type = "string")
                        )
                )
        }
)
public @interface DeleteSections {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
