package com.hiten.docflow.service;

import com.hiten.docflow.dto.AuthRequest;
import com.hiten.docflow.dto.AuthResponse;
import com.hiten.docflow.entity.User;
import com.hiten.docflow.repository.UserRepository;
import com.hiten.docflow.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService — handles register and login
 *
 * REGISTER:
 *   - Check username not taken
 *   - Hash the password (BCrypt — a one-way encryption)
 *   - Save user to DB
 *   - Return token immediately (user is logged in after registration)
 *
 * LOGIN:
 *   - Spring Security verifies username + password (handles wrong credentials automatically)
 *   - Generate and return a JWT token
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;    // BCrypt password hasher

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // REGISTER
    public AuthResponse register(AuthRequest request, String role) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role.toUpperCase()); // e.g. "PATIENT" or "ADMIN"

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return new AuthResponse(token);
    }

    // LOGIN
    public AuthResponse login(AuthRequest request) {
        // This verifies username + password against the DB
        // If wrong, it automatically throws BadCredentialsException
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Fetch user to get their role
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(request.getUsername(), user.getRole());
        return new AuthResponse(token);
    }
}
