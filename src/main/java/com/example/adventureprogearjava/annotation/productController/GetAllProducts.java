package com.example.adventureprogearjava.annotation.productController;

import com.example.adventureprogearjava.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define a method as a GET endpoint to retrieve all products with filters.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.GET)
@Operation(
        summary = "Get all products",
        description = "Retrieves all available products. " +
                "Client is also able to request products using the following filters: " +
                "gender, category, or price.",
        parameters = {
                @Parameter(
                        name = "gender",
                        description = "Gender may be MALE or FEMALE",
                        required = false,
                        in = ParameterIn.QUERY,
                        schema = @Schema(type = "string", format = "string")
                ),
                @Parameter(
                        name = "category",
                        description = "Name of category (only English provided). " +
                                "Current list of possible categories:" +
                                "T-Shirt, Sneakers Hike Model, Butcher knife",
                        required = false,
                        in = ParameterIn.QUERY,
                        schema = @Schema(type = "string", format = "string")
                ),
                @Parameter(
                        name = "priceFrom",
                        description = "Parameter which defines starting price of product.",
                        required = false,
                        in = ParameterIn.QUERY,
                        schema = @Schema(type = "integer", format = "int64")
                ),
                @Parameter(
                        name = "priceTo",
                        description = "Parameter which defines limiting price of product.",
                        required = false,
                        in = ParameterIn.QUERY,
                        schema = @Schema(type = "integer", format = "int64")
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ProductDTO.class)
                        )
                ),

                @ApiResponse(
                        responseCode = "500",
                        description = "Internal server error",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )
        }
)
public @interface GetAllProducts {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}