package com.example.adventureprogearjava.annotation.productContentController;

import com.example.adventureprogearjava.dto.ContentDTO;
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
        summary = "Get content by own ID",
        description = "Retrieves content by its unique ID. The content may include images or other media formats.",
        responses = {

                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = @Content(schema = @Schema(implementation = ContentDTO.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request. The request was invalid or cannot be otherwise served.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found. The requested content was not found.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error. An error occurred on the server side.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface GetContentById {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
