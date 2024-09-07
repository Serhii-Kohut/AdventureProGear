package com.example.adventureprogearjava.annotation.reactionToPostController;

import com.example.adventureprogearjava.dto.ReactionToPostDTO;
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
@RequestMapping(method = RequestMethod.POST)
@Operation(
        summary = "Create or update a reaction to a post.",
        description = "Creates or updates a reaction to a specified post. The post is identified by the postId provided in the URL. The reaction is specified in the request body. The method returns the updated reaction details.",
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
                        responseCode = "201",
                        description = "Reaction created.",
                        content = @Content(schema = @Schema(implementation = ReactionToPostDTO.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized. Authentication is required.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Post not found. The post with the specified postId does not exist.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error. An unexpected error occurred.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface CreateReactionToPost {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
