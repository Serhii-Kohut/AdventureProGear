package com.example.adventureprogearjava.annotation.reactionToPostController;

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
@RequestMapping(method = RequestMethod.DELETE)
@Operation(
        summary = "Delete a reaction for a post.",
        description = "Deletes a reaction for the specified post. This operation will remove the reaction associated with the post and the user.",
        security = @SecurityRequirement(name = "bearerAuth"),
        parameters = @Parameter(
                name = "id",
                description = "ID of Post",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer", format = "int64")
        ),
        responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "No Content. The reaction was successfully removed and no additional content is returned.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request. The request was invalid or the post ID was malformed.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found. The post with the specified ID does not exist, or the reaction was not found.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized. The user making the request is not authorized to perform this operation.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error. An unexpected error occurred while processing the request.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface DeleteReactionOfPost {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
