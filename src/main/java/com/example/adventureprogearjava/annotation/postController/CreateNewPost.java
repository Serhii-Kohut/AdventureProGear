package com.example.adventureprogearjava.annotation.postController;

import com.example.adventureprogearjava.dto.PostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
@RequestMapping(method = RequestMethod.POST)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Operation(
        summary = "Create new post",
        description = "Creates a new post. Only accessible by users with the 'ADMIN' role.",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
                description = "Details of the post including title, content, and image URL.",
                required = true,
                content = @Content(schema = @Schema(implementation = PostDTO.class))
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Post created successfully.",
                        content = @Content(schema = @Schema(implementation = PostDTO.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden. User does not have the 'ADMIN' role.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface CreateNewPost {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
