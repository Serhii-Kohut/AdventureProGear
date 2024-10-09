package com.example.adventureprogearjava.annotation.reviewComment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.GET)
@Operation(
        summary = "Get comment by ID",
        description = "Retrieves a specific comment identified by its ID.",
        parameters = {
                @Parameter(
                        name = "reviewId",
                        description = "ID of the review associated with the comment",
                        required = true,
                        in = ParameterIn.PATH
                ),
                @Parameter(
                        name = "commentId",
                        description = "ID of the comment to be retrieved",
                        required = true,
                        in = ParameterIn.PATH
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = String.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Comment not found",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = String.class)
                        )
                )
        }
)
public @interface GetCommentById {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
