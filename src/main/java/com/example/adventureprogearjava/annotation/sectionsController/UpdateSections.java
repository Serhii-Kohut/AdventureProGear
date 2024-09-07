package com.example.adventureprogearjava.annotation.sectionsController;

import com.example.adventureprogearjava.dto.SectionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
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
        summary = "Update an existing section",
        description = "Updates the details of an existing section specified by the ID.",
        security = @SecurityRequirement(name = "bearerAuth"),
        parameters = @Parameter(
                name = "id",
                description = "ID of the section to be updated",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer", format = "int64")
        ),
        requestBody = @RequestBody(
                description = "Updated section details",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = SectionDTO.class,
                                example = """
                                        {
                                            "sectionCaptionEn": "Clothing",
                                            "sectionCaptionUa": "Одяг",
                                            "sectionIcon": "icon7",
                                            "categories": [
                                                {
                                                    "categoryNameUa": "Футболки",
                                                    "categoryNameEn": "T-shirts",
                                                    "subcategories": [
                                                        {
                                                            "categoryNameUa": "Subcategory UA",
                                                            "categoryNameEn": "Subcategory EN",
                                                            "selfLink": "subcategorySelfLink"
                                                        }
                                                    ],
                                                    "selfLink": "categorySelfLink"
                                                }
                                            ],
                                            "selfLink": "exampleSelfLink",
                                            "categoryCreationLink": "exampleCreationLink"
                                        }
                                        """
                        )
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Section updated successfully.",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(type = "string")
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid data provided.",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(type = "string")
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Section not found.",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(type = "string")
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Server error occurred.",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(type = "string")
                        )
                )
        }
)
public @interface UpdateSections {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
