package com.example.adventureprogearjava.annotation.productReview;

import com.example.adventureprogearjava.dto.ProductReviewDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
        summary = "Create new product review",
        description = "Creates a new product review based on the provided review data.",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
                description = "Product review data required for creation",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProductReviewDTO.class),
                        examples = {
                                @ExampleObject(
                                        name = "Good request",
                                        value = """
                                                {
                                                  "productId": 3,
                                                  "rating": 5,
                                                  "comment": "Excellent"
                                                }
                                                """
                                )
                        }
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successful operation",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ProductReviewDTO.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = String.class)
                        )
                )
        }
)
public @interface CreateReview {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
