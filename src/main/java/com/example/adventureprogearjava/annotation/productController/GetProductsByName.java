package com.example.adventureprogearjava.annotation.productController;

import com.example.adventureprogearjava.dto.ProductDTO;
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
        summary = "Get all products by name",
        description = "Retrieves all available products with the provided name. " +
                "Note: When there are no products with the provided name, the service " +
                "returns just an empty list instead of a 404 status code."
)
@ApiResponse(
        responseCode = "200",
        description = "Successful operation.",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductDTO.class)
        )
)
public @interface GetProductsByName {

    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};

    @Parameter(
            description = "Name of the product to filter by name",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "string")
    )
    String name() default "";
}
