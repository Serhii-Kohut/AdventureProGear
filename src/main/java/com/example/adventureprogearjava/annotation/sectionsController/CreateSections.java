package com.example.adventureprogearjava.annotation.sectionsController;

import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.dto.SectionDTO;
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
        summary = "Create a new section",
        description = "Creates a new section with the provided details.",
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @RequestBody(
                description = "Section details to create",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = SectionDTO.class),

                        examples = {
                                @ExampleObject(
                                        name = "Good request",
                                        value = """
                                                {
                                                    "sectionCaptionEn": "Yoga & Meditation",
                                                    "sectionCaptionUa": "Йога та медитація",
                                                    "sectionIcon": "yoga_icon"
                                                }
                                                """
                                )
                        }
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Section created successfully.",
                        content = @Content(schema = @Schema(implementation = SectionDTO.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data provided.",
                        content = @Content(schema = @Schema(implementation = String.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Server error occurred.",
                        content = @Content(schema = @Schema(implementation = String.class))
                )
        }
)
public @interface CreateSections {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
