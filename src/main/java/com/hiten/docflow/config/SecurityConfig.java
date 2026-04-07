package com.hiten.docflow.config;

import com.hiten.docflow.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig (Simplified)
 *
 * KEY LESSON: In Spring Boot 3, you should NOT manually create
 * a DaoAuthenticationProvider @Bean. Spring Boot auto-wires your
 * UserDetailsService + PasswordEncoder together automatically.
 * Creating it manually causes a conflict with auto-configuration
 * that breaks public endpoints.
 *
 * The right approach: just define UserDetailsService (@Service) and
 * PasswordEncoder (@Bean), and Spring Boot handles the rest.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())                         // No CSRF needed for REST
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No sessions
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/register", "/api/auth/login", "/error").permitAll()
                .anyRequest().authenticated()                     // All other routes → need token
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // BCrypt password hasher — Spring Boot auto-detects this
    // and uses it alongside UserDetailsServiceImpl for authentication
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthService needs this to call authenticationManager.authenticate()
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * CRITICAL FIX: Prevent double filter registration.
     *
     * JwtFilter has @Component, so Spring Boot auto-registers it
     * in the SERVLET filter chain (which runs before Spring Security).
     * We also manually add it inside the security chain via addFilterBefore.
     * Result: the filter runs TWICE — once outside security, once inside.
     * The first run (outside security) interferes before security rules apply.
     *
     * This bean tells Spring Boot: "Don't auto-register JwtFilter as a
     * servlet filter — we're registering it manually inside security."
     */
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter jwtFilter) {
        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>(jwtFilter);
        registration.setEnabled(false); // Disable auto servlet registration
        return registration;
    }
}
