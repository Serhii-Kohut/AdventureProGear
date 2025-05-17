package com.example.adventureprogearjava.annotation.productReview;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
@RequestMapping(method = RequestMethod.POST, path = "/{id}/toggle-like")
@Operation(
        summary = "Toggle like for a product review",
        description = "Toggles the like status for a specific product review identified by its ID. If the review is not liked by the user, it adds a like; if already liked, it removes the like.",
        security = @SecurityRequirement(name = "bearerAuth"),
        parameters = @Parameter(
                name = "id",
                description = "ID of the product review to toggle like",
                required = true,
                in = ParameterIn.PATH
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation, like added or removed, or error if the review is disliked",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = java.util.Map.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Review not found",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = String.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal server error, e.g., user not found",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = String.class)
                        )
                )
        }
)
public @interface ToggleLikeReview {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default "/{id}/toggle-like";
}
