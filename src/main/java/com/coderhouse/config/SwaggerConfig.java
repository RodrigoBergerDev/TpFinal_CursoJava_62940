package com.coderhouse.config;


import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.webmvc.api.OpenApiResource;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ventas Rodrigo Berger - Coderhouse")
                        .version("1.0.0")
                        .description("Proyecto curso Java")
                        .contact(new Contact()
                                .name("Rodrigo Berger")
                                .email("bergerrd@gmail.com")
                                .url("https://coderhouse.com.ar"))                        
                )
                .servers(List.of(new Server()
                        .url("http://localhost:8080")
                        .description("Servidor Local")));

    }
}
