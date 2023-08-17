package com.myapp.omdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    // File needed for restTemplate implementation through all the project
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
