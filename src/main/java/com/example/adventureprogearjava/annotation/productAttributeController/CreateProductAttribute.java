package com.example.adventureprogearjava.annotation.productAttributeController;

import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.POST)
@ResponseStatus(HttpStatus.CREATED)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Operation(
        summary = "Creation of new product attribute",
        description = "Creation of new product attribute. Any product may have different attributes, like size, color, etc. " +
                "Also, this model contains the count of available products in the warehouse.",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
                description = "Product attribute data required for creation",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProductAttributeDTO.class),
                        examples = {
                                @ExampleObject(
                                        name = "Good request",
                                        value = """
                                               {
                                                  "productId": 3,
                                                  "size": "XL",
                                                  "color": "Black",
                                                  "additional": "Waterproof",
                                                  "priceDeviation": 0,
                                                  "quantity": 5,
                                                  "selfLink": "/products/1/attributes/1"
                                                }
                                                """
                                )
                        }
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successful operation.",
                        content = @Content(schema = @Schema(implementation = ProductAttributeDTO.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface CreateProductAttribute {

    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};

}
