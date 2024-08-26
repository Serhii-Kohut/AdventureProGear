package com.example.adventureprogearjava.annotation.productController;

import com.example.adventureprogearjava.dto.ProductDTO;
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
        summary = "Creation of new product",
        description = "Creation of new product",
        security = @SecurityRequirement(name = "bearerAuth")
)
@RequestBody(
        description = "Product data required for creation",
        required = true,
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductDTO.class),
                examples = {
                        @ExampleObject(
                                name = "Good request",
                                value = """
                                        {
                                          "productNameUa": "Кросівки",
                                          "productNameEn": "Sneakers",
                                          "descriptionUa": "Зручні спортивні кросівки",
                                          "descriptionEn": "Comfortable sports sneakers",
                                          "basePrice": 2000,
                                          "gender": "MALE",
                                          "category": {
                                            "categoryId": 1,
                                            "categoryName": "Shoes"
                                          },
                                          "attributes": [
                                            {
                                              "attributeId": 1,
                                              "attributeName": "Color",
                                              "attributeValue": "Red"
                                            }
                                          ],
                                          "contents": [
                                            {
                                              "contentId": 1,
                                              "contentType": "IMAGE",
                                              "contentUrl": "/images/sneakers.jpg"
                                            }
                                          ]
                                        }
                                        """
                        )
                }
        )
)
@ApiResponse(
        responseCode = "201",
        description = "Successful operation.",
        content = @Content(schema = @Schema(implementation = ProductDTO.class))
)
@ApiResponse(
        responseCode = "403",
        description = "Access Denied",
        content = @Content(schema = @Schema(implementation = String.class))
)
@ApiResponse(
        responseCode = "400",
        description = "Invalid data",
        content = @Content(schema = @Schema(implementation = String.class))
)
public @interface CreateProduct {

    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};

}