package com.example.ecommerce_spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
// link between yaml this class and the attributes that found below spring.jwt in yaml file
@ConfigurationProperties(prefix = "spring.jwt")
public class JwtConfiguration {
    private int accessTokenExpiration;
    private int refreshTokenExpiration;
    private String secret;
}
