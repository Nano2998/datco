package com.example.datco.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwtSchemeName = "jwtAuth";
        SecurityRequirement securityRequirement =
                new SecurityRequirement().addList(jwtSchemeName);

        Components components = new Components()
                .addSchemas("LocalTime", new StringSchema().example("14:30:30"))
                .addSecuritySchemes(jwtSchemeName,
                        new SecurityScheme()
                                .name(jwtSchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));

        return new OpenAPI()
                .openapi("3.0.1")
                .components(components)
                .info(apiInfo())
                .addSecurityItem(securityRequirement);
    }

    private io.swagger.v3.oas.models.info.Info apiInfo() {
        return new Info()
                .title("Datco REST API Specifications")
                .description("Specification")
                .version("1.0.0");
    }
}
