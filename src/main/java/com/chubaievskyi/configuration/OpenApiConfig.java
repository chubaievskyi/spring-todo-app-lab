package com.chubaievskyi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${project.version}")
    private String version;

    @Value("${open.api.local.server.url}")
    private String localServer;

    @Value("${open.api.remote.server.url}")
    private String remoteServer;

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("Java Dev Tools. Spring. Homework No.2.")
                        .version(version)
                        .description("OpenApi documentation for spring-todo-app")
                        .contact(new Contact()
                                .name("Pavlo Chubaievskyi")
                                .email("chubaievskyi@gmail.com")
                                .url("https://github.com/chubaievskyi"))
                        .license(new License()
                                .name("Licence name")
                                .url("https://some-url.com")
                        )
                ).servers(
                        List.of(
                                new Server().url(localServer).description("DEV Server"),
                                new Server().url(remoteServer).description("PROD Server")
                        )
                );
    }
}