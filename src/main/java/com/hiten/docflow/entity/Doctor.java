package com.hiten.docflow.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Doctor Entity
 *
 * This becomes the "doctors" table in the database.
 * Each Doctor has a name, specialization, and phone number.
 */
@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialization;  // e.g. "Cardiologist", "Dentist", "General"

    @Column(unique = true)
    private String phone;
}
