package com.hiten.docflow.service;

import com.hiten.docflow.entity.Appointment;
import com.hiten.docflow.entity.Doctor;
import com.hiten.docflow.entity.Patient;
import com.hiten.docflow.repository.AppointmentRepository;
import com.hiten.docflow.repository.DoctorRepository;
import com.hiten.docflow.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Appointment Service (Floor 2)
 *
 * This is the most important service — it contains the core
 * business rules of the hospital system:
 *  1. Patient must exist before booking
 *  2. Doctor must exist before booking
 *  3. Doctor must be available on the requested date (no double-booking)
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;  // Needed to verify patient exists

    @Autowired
    private DoctorRepository doctorRepository;    // Needed to verify doctor exists

    // BOOK an appointment
    public Appointment bookAppointment(Long patientId, Long doctorId, Appointment appointment) {

        // Rule 1: Check patient exists
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        // Rule 2: Check doctor exists
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        // Rule 3: Check doctor is not already booked on this date
        boolean isBooked = appointmentRepository.existsByDoctor_IdAndAppointmentDate(
                doctorId, appointment.getAppointmentDate());

        if (isBooked) {
            throw new RuntimeException(
                "Doctor is not available on " + appointment.getAppointmentDate()
            );
        }

        // All checks passed — create the appointment
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus("BOOKED");

        return appointmentRepository.save(appointment);
    }

    // GET all appointments
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // GET appointments for a specific patient
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatient_Id(patientId);
    }

    // GET appointments for a specific doctor
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctor_Id(doctorId);
    }

    // CANCEL an appointment (just changes status, doesn't delete)
    public Appointment cancelAppointment(Long appointmentId) {
        Optional<Appointment> optional = appointmentRepository.findById(appointmentId);

        if (optional.isEmpty()) {
            throw new RuntimeException("Appointment not found with id: " + appointmentId);
        }

        Appointment appointment = optional.get();
        appointment.setStatus("CANCELLED");
        return appointmentRepository.save(appointment);
    }
}
