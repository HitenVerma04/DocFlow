package com.hiten.docflow.repository;

import com.hiten.docflow.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Appointment Repository (Floor 1)
 * 
 * Notice: these derived queries are more complex because
 * Appointment links to both Patient and Doctor.
 * 
 * Spring understands "findByPatient_Id" as:
 * → Join appointments with patients WHERE patient.id = ?
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Get all appointments for a specific patient
    List<Appointment> findByPatient_Id(Long patientId);

    // Get all appointments for a specific doctor
    List<Appointment> findByDoctor_Id(Long doctorId);

    // Check if a doctor is already booked on a specific date
    // (Used to prevent double-booking)
    boolean existsByDoctor_IdAndAppointmentDate(Long doctorId, LocalDate date);
}
