package com.chubaievskyi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Value("${project.version}")
    private String version;

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("Java Dev Tools. Spring. Homework No.1.")
                        .version(version)
                        .description("""
                                        Implement a controller with four http methods.
                                        Implement an individual tax number validator.
                                        Register the API on AWS Elastic BeanStalk.
                                     """)
                        .contact(new Contact()
                                .name("Pavlo Chubaievskyi")
                                .email("chubaievskyi@gmail.com"))
                );
    }
}