package com.example.adventureprogearjava.annotation.categoryController;

import com.example.adventureprogearjava.dto.SubSubCategoryDTO;
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
@RequestMapping(method = RequestMethod.POST)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Operation(
        summary = "Creation of new sub-subcategory",
        description = "Creation of new sub-subcategory",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
                description = "Sub-subcategory data, required for creation",
                required = true,
                content = @Content(
                        schema = @Schema(implementation = SubSubCategoryDTO.class),
                        examples = {
                                @ExampleObject(
                                        name = "Sub-Subcategory Example",
                                        description = "Example of a sub-subcategory creation request",
                                        value = "{\"subSubCategoryNameUa\": \"Супер Толстовка\"," +
                                                " \"subSubCategoryNameEn\": \"Super Jumper\"}"
                                )
                        }
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successful operation.",
                        content = @Content(schema = @Schema(implementation = SubSubCategoryDTO.class))
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
public @interface CreateSubSubCategory {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
