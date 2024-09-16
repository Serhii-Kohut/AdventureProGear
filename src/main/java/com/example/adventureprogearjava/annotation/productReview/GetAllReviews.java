package com.example.adventureprogearjava.annotation.productReviewController;

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
        summary = "Get all product reviews",
        description = "Retrieves all product reviews. " +
                "Client can also filter reviews by product ID and rating range.",
        parameters = {
                @Parameter(
                        name = "productId",
                        description = "ID of the product to filter reviews by",
                        required = false,
                        in = ParameterIn.QUERY,
                        schema = @Schema(type = "integer", format = "int64")
                ),
                @Parameter(
                        name = "ratingFrom",
                        description = "Minimum rating to filter reviews",
                        required = false,
                        in = ParameterIn.QUERY,
                        schema = @Schema(type = "number", format = "double")
                ),
                @Parameter(
                        name = "ratingTo",
                        description = "Maximum rating to filter reviews",
                        required = false,
                        in = ParameterIn.QUERY,
                        schema = @Schema(type = "number", format = "double")
                )
        },
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
                        responseCode = "500",
                        description = "Internal server error",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = String.class)
                        )
                )
        }
)
public @interface GetAllReviews {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};

}
