package com.hiten.docflow.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * User Entity — becomes the "users" table in PostgreSQL
 *
 * This is NOT the same as a Patient or Doctor.
 * This is the LOGIN account — who can log into the system.
 *
 * In Module 10 (Role-based access), the role field will control
 * what each user is allowed to do (ADMIN can delete, PATIENT can only view self).
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;             // e.g. "rahul@hospital.com"

    @Column(nullable = false)
    private String password;            // STORED AS HASH, never plain text!

    @Column(nullable = false)
    private String role;                // "ADMIN", "DOCTOR", or "PATIENT"
}
