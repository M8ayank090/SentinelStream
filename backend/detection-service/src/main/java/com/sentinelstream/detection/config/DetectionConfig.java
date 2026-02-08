package com.sentinelstream.detection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Detection service configuration
 */
@Configuration
public class DetectionConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
