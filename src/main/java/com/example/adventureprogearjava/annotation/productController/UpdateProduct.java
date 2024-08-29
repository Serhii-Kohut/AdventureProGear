package com.example.adventureprogearjava.annotation.productController;

import com.example.adventureprogearjava.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
@RequestMapping(method = RequestMethod.PUT)
@ResponseStatus(HttpStatus.OK)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Operation(
        summary = "Update of the product",
        description = "Update of the product",
        security = @SecurityRequirement(name = "bearerAuth")
)
@RequestBody(
        description = "Product data required for Update",
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
                                          "basePrice": 1500,
                                          "gender": "MALE",
                                          "category": {
                                            "categoryId": 1,
                                            "categoryName": "Shoes"
                                          },
                                          "attributes": [
                                            {
                                              "attributeId": 1,
                                              "attributeName": "Color",
                                              "attributeValue": "White"
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
        responseCode = "200",
        description = "Successful operation."
)
@ApiResponse(
        responseCode = "403",
        description = "Access Denied",
        content = @Content(schema = @Schema(implementation = String.class))
)
@ApiResponse(
        responseCode = "404",
        description = "Not Found",
        content = @Content(schema = @Schema(implementation = String.class))
)
@ApiResponse(
        responseCode = "400",
        description = "Invalid data",
        content = @Content(schema = @Schema(implementation = String.class))
)
public @interface UpdateProduct {

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