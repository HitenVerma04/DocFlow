package com.hiten.docflow.controller;

import com.hiten.docflow.entity.Appointment;
import com.hiten.docflow.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Appointment Controller (Floor 3)
 *
 * Notice the URL design:
 *   /api/appointments/book?patientId=1&doctorId=2
 * 
 * We use @RequestParam to read the "?patientId=1" from the URL.
 * This is different from @PathVariable which reads /api/appointments/{id}
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // POST /api/appointments/book?patientId=1&doctorId=2
    // Body: { "appointmentDate": "2026-04-15" }
    @PostMapping("/book")
    public ResponseEntity<Appointment> bookAppointment(
            @RequestParam Long patientId,    // reads ?patientId=1 from the URL
            @RequestParam Long doctorId,     // reads &doctorId=2 from the URL
            @RequestBody Appointment appointment) {  // reads the date from the body

        Appointment booked = appointmentService.bookAppointment(patientId, doctorId, appointment);
        return new ResponseEntity<>(booked, HttpStatus.CREATED);
    }

    // GET /api/appointments → all appointments
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    // GET /api/appointments/patient/1 → appointments for patient ID 1
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientId));
    }

    // GET /api/appointments/doctor/1 → appointments for doctor ID 1
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctorId));
    }

    // PUT /api/appointments/1/cancel → cancel appointment with ID 1
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    }
}
