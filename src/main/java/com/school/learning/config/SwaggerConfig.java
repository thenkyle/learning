package com.school.learning.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(this.newComponent())
                .info(this.newInfo());
    }

    private Components newComponent() {
        return new Components().addSecuritySchemes("Bearer Authentication", this.createAPIKeyScheme());
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
    /** SwaggerUI上方資訊設定 */
    private Info newInfo() {
        return new Info()
                .title("MISYS Teach REST API")
                .description("補習班系統相關REST API.")
                .version("1.0")
                .contact(new Contact()
                        .name("後端專案GitHub連結")
                        .url("https://github.com/thenkyle/learning"));
    }

}
