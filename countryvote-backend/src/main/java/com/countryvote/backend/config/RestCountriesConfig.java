package com.countryvote.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestCountriesConfig {

    @Bean
    public WebClient restCountriesClient() {
        return WebClient.builder()
                .baseUrl("https://restcountries.com/v3.1")
                .build();
    }

}
