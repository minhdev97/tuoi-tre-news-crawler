package com.meu.news_crawler_be.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Tuoi Tre News crawler API",
                description = "API endpoints to get data for Tuoi Tre News crawler",
                version = "1.0-alpha"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "REST Server URL"
                )
        }
)

public class ApiSpecification {

}
