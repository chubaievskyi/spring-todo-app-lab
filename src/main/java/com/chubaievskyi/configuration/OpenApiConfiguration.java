package com.chubaievskyi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OpenApiConfiguration {

    @Value("${project.version}")
    String version;

    @Value("${open.api.local.server.url}")
    String localServer;

    @Value("${open.api.remote.server.url}")
    String remoteServer;

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI()
                .openapi("3.0.0")
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
                )
                .addSecurityItem(new SecurityRequirement())
                .components(
                        new Components()
                                .addSecuritySchemes("todo-app",
                                        new SecurityScheme().name("todo-app")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("basic")
                                                .in(SecurityScheme.In.HEADER)

                                )
                                .addParameters("Accept-Language",
                                        new Parameter()
                                                .name("Accept-Language")
                                                .in("header")
                                                .description("Мова (uk), Language(en), Sprache(de)")
                                                .required(false)
                                                .schema(new Schema<String>()
                                                        .type("string")
                                                        .example("en")
                                                )
                                                .example("en")
                                )

                );
    }
}