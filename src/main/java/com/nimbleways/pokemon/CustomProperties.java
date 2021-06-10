package com.nimbleways.pokemon;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "abdelouahed.app")
public class CustomProperties {
    String jwtSecret;
    Integer jwtExpirationMs;
}
