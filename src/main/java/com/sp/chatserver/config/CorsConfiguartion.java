package com.sp.chatserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfiguartion {
    @Value("${cors.allowed-origin-patterns}")
    private String allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        ChatCorsConfiguration corsConfiguration = new ChatCorsConfiguration(allowedOrigins);
        corsConfiguration.registerCorsConfiguration("/**", new CorsConfiguration());
        return corsConfiguration;
    }

    @Bean("chatCorsFilter")
    public CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource) {
        final CorsFilter corsFilter = new CorsFilter(corsConfigurationSource);
        corsFilter.setCorsProcessor(new ChatCorsProcessor(allowedOrigins));
        return corsFilter;
    }
}
