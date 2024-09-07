package com.example.adventureprogearjava.annotation.sectionsController;

import com.example.adventureprogearjava.dto.SectionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
        summary = "Get all sections",
        description = "Retrieve a list of all sections.",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved the list of sections.",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = SectionDTO.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal server error"
                )
        }
)
public @interface GetAllSections {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
