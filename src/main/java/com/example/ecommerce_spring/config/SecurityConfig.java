package com.example.ecommerce_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    return http
            // tha below line says that we use web token not sessions
            .sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // the below line disable csrf we don't need it because we are not using sessions and cookies
            .csrf(AbstractHttpConfigurer::disable)
            // the below line says wich request are need to be authenticated and which are not
            // and disable the default login page of spring security
            .authorizeHttpRequests(c-> c
                    .requestMatchers("/carts/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/users").permitAll()
                    .requestMatchers("/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**",
                            "/v3/api-docs.yaml",
                            "/swagger-resources/**",
                            "/webjars/**").permitAll()
                    .anyRequest().authenticated()

            )
            .build();
}
}
