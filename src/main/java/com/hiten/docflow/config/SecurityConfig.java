package com.hiten.docflow.config;

import com.hiten.docflow.security.JwtFilter;
import com.hiten.docflow.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig — The master rulebook for who can access what.
 *
 * KEY CONCEPTS:
 *
 * 1. STATELESS session: REST APIs don't use sessions/cookies.
 *    Each request must carry its own token. This is the industry standard.
 *
 * 2. CSRF disabled: CSRF attacks exploit browser cookies/sessions.
 *    Since we're stateless (no sessions), CSRF doesn't apply.
 *
 * 3. JwtFilter runs BEFORE UsernamePasswordAuthenticationFilter:
 *    Our filter intercepts the request, reads the token, and tells
 *    Spring Security who is making the request.
 *
 * WHAT IS PERMITTED:
 *   /api/auth/**  → public (anyone can register/login)
 *   everything else → requires valid JWT token
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())                           // Disable CSRF (not needed for REST)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No sessions!
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()        // Login/Register: open to all
                .anyRequest().authenticated()                      // Everything else: needs token
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add our filter

        return http.build();
    }

    // BCrypt: industry standard password hashing algorithm
    // Same password hashed twice gives different results (uses salt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Wires together: UserDetailsService + PasswordEncoder for authentication
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // AuthenticationManager is what AuthService uses to verify login credentials
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
