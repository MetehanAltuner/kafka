package com.demo.kafka.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kafka Integration API")
                        .version("1.0")
                        .description("Bu API, kullanıcıların Kafka üzerinden veri akışı yapılandırmaları yapmasına ve veri akışlarını yönetmesine olanak sağlar."));
    }

}

