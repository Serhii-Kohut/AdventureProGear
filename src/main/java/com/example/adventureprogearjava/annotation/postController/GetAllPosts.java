package com.example.adventureprogearjava.annotation.postController;

import com.example.adventureprogearjava.dto.PostDTO;
import io.swagger.v3.oas.annotations.Operation;
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
        summary = "Get all posts",
        description = "Retrieves all available posts.",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation.",
                        content = @Content(schema = @Schema(implementation = PostDTO.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "No posts found.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface GetAllPosts {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
