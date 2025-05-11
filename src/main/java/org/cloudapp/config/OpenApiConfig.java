package org.cloudapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
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
                        .title("ExerciseTrackingAPI")
                        .description("A REST API for tracking exercise activities. Made as a project for the Distributed Computing class at UFSC.")
                        .version("v1.0.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("distribuida-t1.brazilsouth.cloudapp.azure.com:8080")
                                .description("Cloud application exercise tracker"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server")));
    }
}