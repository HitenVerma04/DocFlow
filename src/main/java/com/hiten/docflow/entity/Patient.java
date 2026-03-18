package com.hiten.docflow.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Patient Entity
 *
 * This Java class becomes the "patients" table in the database.
 * Each field below becomes a column in that table.
 *
 * @Entity      → tells Spring "this class = a database table"
 * @Table       → specifies the table name
 * @Id          → this field is the primary key
 * @GeneratedValue → auto-increment (1, 2, 3...)
 */
@Entity
@Table(name = "patients")
@Getter                 // Lombok: auto-generates getters for all fields
@Setter                 // Lombok: auto-generates setters for all fields
@NoArgsConstructor      // Lombok: auto-generates empty constructor
@AllArgsConstructor     // Lombok: auto-generates constructor with all fields
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)   // This column cannot be empty
    private String name;

    private int age;

    private String gender;

    @Column(unique = true)      // No two patients can have the same phone number
    private String phone;

    private String address;
}
