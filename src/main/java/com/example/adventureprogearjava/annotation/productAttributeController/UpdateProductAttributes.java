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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.PUT)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Operation(
        summary = "Update of the product attribute",
        description = "Update of the product attribute",
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
                                                  "size": "S",
                                                  "color": "White",
                                                  "additional": "Waterproof",
                                                  "priceDeviation": 0,
                                                  "quantity": 10,
                                                  "selfLink": "/products/1/attributes/1"
                                                }
                                                """
                                )
                        }
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation."
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)

public @interface UpdateProductAttributes {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};

}
