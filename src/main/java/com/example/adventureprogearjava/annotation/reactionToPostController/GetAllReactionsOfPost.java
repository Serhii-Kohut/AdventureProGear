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
import java.util.Map;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.GET)
@Operation(
        summary = "Count all reactions for a post.",
        description = "Counts and returns the number of reactions for the specified post. The reactions are categorized by type and their counts are returned as a map.",
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
                        responseCode = "200",
                        description = "Successful operation. The count of all reactions categorized by type.",
                        content = @Content(schema = @Schema(implementation = Map.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request. The request was invalid or the post ID was malformed.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found. The post with the specified ID does not exist.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error. An unexpected error occurred while processing the request.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface GetAllReactionsOfPost {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
