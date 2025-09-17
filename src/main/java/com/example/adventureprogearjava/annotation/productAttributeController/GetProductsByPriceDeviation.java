package com.example.adventureprogearjava.annotation.productAttributeController;

import com.example.adventureprogearjava.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.query.Page;
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
        summary = "Отримати продукти за відхиленням ціни з пагінацією",
        description = "Повертає сторінку продуктів, які мають атрибути з відхиленням ціни більшим за вказане значення"
)
@Parameter(
        name = "minPriceDeviation",
        description = "Мінімальне відхилення ціни атрибута продукту",
        required = false,
        in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
        schema = @Schema(type = "integer", defaultValue = "0")
)
@Parameter(
        name = "page",
        description = "Номер сторінки (починається з 0)",
        required = false,
        in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
        schema = @Schema(type = "integer", defaultValue = "0")
)
@Parameter(
        name = "size",
        description = "Розмір сторінки (кількість записів на сторінці)",
        required = false,
        in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
        schema = @Schema(type = "integer", defaultValue = "20")
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Успішна операція",
                content = @Content(
                        schema = @Schema(implementation = Page.class, subTypes = {ProductDTO.class})
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Не знайдено продуктів із вказаним відхиленням ціни",
                content = @Content(
                        schema = @Schema(implementation = String.class)
                )
        )
})
public @interface GetProductsByPriceDeviation {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
