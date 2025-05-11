package org.cloudapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI exerciseTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Exercise Tracker API")
                        .description("A REST API for tracking exercise activities")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Exercise Tracker Team")
                                .email("contact@exercisetracker.com")
                                .url("https://exercisetracker.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api.exercisetracker.com")
                                .description("Production Server")));
    }
}