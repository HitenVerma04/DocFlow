package com.hiten.docflow.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtFilter — The Security Guard at the Door
 *
 * This runs on EVERY incoming HTTP request, BEFORE it reaches your Controller.
 * It checks:
 *   1. Is there an "Authorization" header?
 *   2. Does it start with "Bearer "? (JWT standard format)
 *   3. Is the token valid?
 *   4. If all yes → let the request through as authenticated
 *   5. If no → the request continues but as anonymous (SecurityConfig will block it)
 *
 * Request flow with filter:
 *   HTTP Request → JwtFilter → SecurityConfig checks → Controller
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Step 1: Get the Authorization header
        // It should look like: "Bearer eyJhbGciOi..."
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        // Step 2: Extract the token from the header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Remove "Bearer " prefix
            username = jwtUtil.extractUsername(token);
        }

        // Step 3: If we found a valid username and user isn't already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Step 4: Validate the token
            if (jwtUtil.isTokenValid(token, userDetails.getUsername())) {
                // Step 5: Tell Spring Security this request is authenticated
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Step 6: Continue to the next filter / controller
        filterChain.doFilter(request, response);
    }
}
