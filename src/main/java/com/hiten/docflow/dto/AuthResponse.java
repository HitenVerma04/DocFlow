package com.hiten.docflow.dto;

import lombok.*;

/**
 * AuthResponse DTO
 *
 * This is what the server sends BACK after a successful login.
 * Just the token — nothing else.
 *
 * The client (React/Postman) stores this token and sends it
 * with every future request in the "Authorization" header.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
