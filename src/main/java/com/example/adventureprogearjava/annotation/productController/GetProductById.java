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
        summary = "Get product by its own id",
        description = "Retrieves Product by id"
)
@ApiResponse(
        responseCode = "200",
        description = "Successful operation",
        content = @Content(schema = @Schema(implementation = ProductDTO.class))
)
@ApiResponse(
        responseCode = "404",
        description = "Not Found",
        content = @Content(schema = @Schema(implementation = String.class))
)
public @interface GetProductById {

    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};

    @Parameter(
            description = "ID of the product",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "integer", format = "int64")
    )
    long id() default 0L;
}