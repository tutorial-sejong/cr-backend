package com.tutorialsejong.courseregistration.common.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("https://tutorial-sejong.com").description("Production Server"),
                        new Server().url("http://dev-tutorial-sejong.o-r.kr:8090").description("Test Server"),
                        new Server().url("http://localhost:8080").description("Local Server")
                ))
                // API 정보
                .info(new Info()
                        .title("Tutorial Sejong API")
                        .version("v2.0.0")
                        .description("Tutorial Sejong API API")
                );
    }
}
