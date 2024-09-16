package com.example.adventureprogearjava.annotation.productReview;

import com.example.adventureprogearjava.dto.ProductReviewDTO;
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
        summary = "Get product review by ID",
        description = "Retrieves a specific product review identified by its ID.",
        parameters = @Parameter(
                name = "id",
                description = "ID of the product review",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer", format = "int64")
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ProductReviewDTO.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Review not found",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = String.class)
                        )
                )
        }
)
public @interface GetReviewById {
        @AliasFor(annotation = RequestMapping.class, attribute = "path")
        String[] path() default {};
}
