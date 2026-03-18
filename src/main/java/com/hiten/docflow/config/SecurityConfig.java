package com.hiten.docflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration
 *
 * Right now: allows ALL requests without login (so we can test our APIs easily).
 * Later (Module 9): we'll add JWT authentication here.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())              // Disable CSRF (not needed for REST APIs)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()               // Allow all requests for now
            );

        return http.build();
    }
}
