package com.hiten.docflow.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Appointment Entity
 *
 * This becomes the "appointments" table.
 * It connects a Patient to a Doctor on a specific date.
 *
 * @ManyToOne → means "many appointments can belong to one patient"
 * @JoinColumn → specifies the foreign key column name in the database
 */
@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne                              // Many appointments → one patient
    @JoinColumn(name = "patient_id")        // Creates "patient_id" column in appointments table
    private Patient patient;

    @ManyToOne                              // Many appointments → one doctor
    @JoinColumn(name = "doctor_id")         // Creates "doctor_id" column in appointments table
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    private String status;  // e.g. "BOOKED", "CANCELLED", "COMPLETED"
}
