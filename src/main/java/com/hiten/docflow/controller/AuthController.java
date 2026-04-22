package com.hiten.docflow.controller;

import com.hiten.docflow.dto.AuthRequest;
import com.hiten.docflow.dto.AuthResponse;
import com.hiten.docflow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController — public endpoints, no token required
 *
 * POST /api/auth/register  → creates new account, returns token
 * POST /api/auth/login     → verifies credentials, returns token
 *
 * @Valid triggers validation annotations from AuthRequest
 * (e.g. @NotBlank will reject empty username/password)
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody AuthRequest request,
            @RequestParam(defaultValue = "PATIENT") String role) {
        return ResponseEntity.ok(authService.register(request, role));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
