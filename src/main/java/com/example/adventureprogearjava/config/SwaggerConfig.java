package com.example.adventureprogearjava.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    /*
    Основные настройки для swagger на сервере https:
    * */
    @Bean
    public OpenAPI adventureProGearOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Adventure Pro Gear API")
                        .description("This Swagger API prototype outlines core functionalities for the Adventure Pro Gear online store, including:\n\n" +
                                "- Travel accessories management,\n" +
                                "- User registration and authentication,\n" +
                                "- Product pagination and filtration,\n" +
                                "- Order processing and payment integration.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("TeamChallenge Web-Project")
                                .url("https://github.com/Serhii-Kohut/AdventureProGear")
                        )
                )
                .servers(List.of(
                        new Server().url("https://authentic-laughter-production.up.railway.app")
                ))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }

    /*
    Настройки для локального тестирования swagger localhost http
     */

//    @Bean
//    public OpenAPI adventureProGearOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("Adventure Pro Gear API")
//                        .description("This Swagger API prototype outlines core functionalities for the Adventure Pro Gear online store, including:\n\n" +
//                                "- Travel accessories management,\n" +
//                                "- User registration and authentication,\n" +
//                                "- Product pagination and filtration,\n" +
//                                "- Order processing and payment integration.")
//                        .version("1.0")
//                        .contact(new Contact()
//                                .name("TeamChallenge Web-Project")
//                                .url("https://github.com/Serhii-Kohut/AdventureProGear")
//                        )
//                )
//
//                .components(new Components()
//                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
//                                .name("Authorization")
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("bearer")
//                                .bearerFormat("JWT")
//                        )
//                );
//    }


}