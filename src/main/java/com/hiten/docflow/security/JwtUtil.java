package com.hiten.docflow.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JwtUtil — The "ID Card Machine"
 *
 * Three jobs:
 * 1. generateToken()  → creates a new JWT for a logged-in user
 * 2. extractUsername() → reads who the token belongs to
 * 3. isTokenValid()   → checks the token hasn't expired or been tampered with
 *
 * The SECRET KEY is used to "sign" the token like a wax seal on a letter.
 * Anyone can read the token body, but only YOUR server can verify it's genuine.
 */
@Component
public class JwtUtil {

    // This secret string is used to sign every token
    // In production, this would be stored in environment variables, NOT in code!
    private static final String SECRET = "docflow-super-secret-key-for-jwt-signing-2026";

    // Token valid for 24 hours (in milliseconds)
    private static final long EXPIRATION_MS = 1000L * 60 * 60 * 24;

    // Convert our string secret into a cryptographic key
    private SecretKey getKey() {
        byte[] keyBytes = SECRET.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JOB 1: Create a token for a username AND embed their role
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)                           // Role stored inside the token!
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getKey())
                .compact();
    }

    // NEW: Read the role from a token
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // JOB 2: Read the username from a token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // JOB 3: Check if token is still valid (not expired, not tampered)
    public boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    // Helper: read the body of a token
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Helper: check if token's expiry date is in the past
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
