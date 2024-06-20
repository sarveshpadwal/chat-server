package com.sp.chatserver.config;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChatCorsConfiguration extends UrlBasedCorsConfigurationSource {

    static final Long CORS_MAX_AGE_IN_SEC = 3600L;

    static final List<String> ALLOWED_HEADERS = Arrays.asList(
            "Accept", "Accept-Language", "Content-Language",
            "Content-Type", "Authorization", "Origin", "Referer");

    private final List<String> allowedOrigins;

    public ChatCorsConfiguration(String allowedUrls) {
        allowedOrigins = Optional.ofNullable(allowedUrls)
                .map(urls -> Arrays.stream(urls.split(","))
                        .toList())
                .orElse(Collections.emptyList());
    }

    @Override
    public void registerCorsConfiguration(String path, CorsConfiguration configuration) {
        if (allowedOrigins.contains("*")) {
            configuration.addAllowedOriginPattern("*");
        } else {
            configuration.setAllowedOrigins(allowedOrigins);
        }
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE",
                "OPTIONS", "HEAD", "PATCH"));
        configuration.setMaxAge(CORS_MAX_AGE_IN_SEC);
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);

        super.registerCorsConfiguration(path, configuration);
    }
}
