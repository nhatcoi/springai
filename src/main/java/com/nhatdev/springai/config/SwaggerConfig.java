package com.nhatdev.springai.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger config
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springAiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring AI Chat API")
                        .description("API cho ứng dụng chat sử dụng Spring AI và OpenAI")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("NhatDev")
                                .email("contact@nhatdev.com")
                                .url("https://github.com/nhatdev"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8888")
                                .description("Development server"),
                        new Server()
                                .url("https://api.example.com")
                                .description("Production server (placeholder)")
                ));
    }
}