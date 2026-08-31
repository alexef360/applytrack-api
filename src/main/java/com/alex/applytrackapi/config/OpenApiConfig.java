package com.alex.applytrackapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI applyTrackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ApplyTrack API")
                        .description("REST API to track job and internship applications")
                        .version("1.0"));
    }
}
