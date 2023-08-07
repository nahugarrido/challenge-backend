package com.techforb.challenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(name = "bearerAuth",
description = "JWT auth description",
scheme = "bearer",
type = SecuritySchemeType.HTTP,
bearerFormat = "JWT",
in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().servers(List.of(new Server().url("https://challenge-backend-production.up.railway.app/"),
                        new Server().url("http://challenge-backend-production.up.railway.app/"),
                        new Server().url("https://localhost:8080"),
        new Server().url("http://localhost:8080")
        ))
                .info(new Info().title("Spring Boot 3 API - Credit App Challenge")
                        .version("0.11")
                        .description("")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}

