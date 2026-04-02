package com.hiten.docflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * AuthRequest DTO (Data Transfer Object)
 *
 * What is a DTO?
 * It's a simple class used to carry data between layers.
 * We use it for the REQUEST body when user registers or logs in.
 *
 * WHY not use the User entity directly?
 * Because the User entity has fields like id, role etc. that
 * the user shouldn't be sending. DTOs give you control over
 * exactly what data comes IN and goes OUT.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;
}
