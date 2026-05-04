package com.hiten.docflow.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig — customizes the auto-generated API documentation.
 *
 * Without this: Swagger UI works but has no way to enter your JWT token.
 * With this:    Swagger UI has an "Authorize" button where you paste your token
 *               and then test ALL protected endpoints directly from the browser.
 *
 * OpenAPI = the standard specification format for API documentation.
 * Swagger UI = a web interface that reads OpenAPI specs and renders them visually.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI docFlowOpenAPI() {
        // Define "BearerAuth" as a security scheme
        // This tells Swagger: "requests can be authenticated with a JWT Bearer token"
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("BearerAuth");

        return new OpenAPI()
                // API metadata — shown at the top of the Swagger UI page
                .info(new Info()
                        .title("DocFlow Hospital Management API")
                        .description("REST API for managing patients, doctors, and appointments. " +
                                "Register or login first to get a JWT token, then click 'Authorize' to test protected endpoints.")
                        .version("1.0.0"))
                // Register the security scheme globally
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", bearerAuth))
                // Apply it to all endpoints by default
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
    }
}
