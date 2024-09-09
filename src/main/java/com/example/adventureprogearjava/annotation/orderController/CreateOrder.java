package com.example.adventureprogearjava.annotation.orderController;

import com.example.adventureprogearjava.dto.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.POST)
@Operation(
        summary = "Creation of new order",
        description = "Creation of new order.",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
                description = "Order data, required for creation",
                required = true,
                content = @Content(
                        schema = @Schema(implementation = OrderDTO.class),
                        examples = @ExampleObject(name = "Order Example",
                                value = "{\n" +
                                        "  \"city\": \"New York\",\n" +
                                        "  \"postAddress\": \"123 Elm Street\",\n" +
                                        "  \"comment\": \"Please deliver between 9 AM and 5 PM.\",\n" +
                                        "  \"price\": 1200,\n" +
                                        "  \"ordersLists\": [\n" +
                                        "    {\n" +
                                        "      \"productId\": 2,\n" +
                                        "      \"quantity\": 2,\n" +
                                        "      \"price\": 600\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "      \"productId\": 3,\n" +
                                        "      \"quantity\": 1,\n" +
                                        "      \"price\": 600\n" +
                                        "    }\n" +
                                        "  ]\n" +
                                        "}")
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successful operation.",
                        content = @Content(schema = @Schema(implementation = OrderDTO.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface CreateOrder {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
