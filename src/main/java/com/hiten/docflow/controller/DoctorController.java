package com.hiten.docflow.controller;

import com.hiten.docflow.entity.Doctor;
import com.hiten.docflow.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Doctor Controller (Floor 3 - The Receptionist)
 * 
 * All Doctor API endpoints live here.
 * Base URL: http://localhost:8080/api/doctors
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // POST /api/doctors
    // Body: { "name": "Dr. Sharma", "specialization": "Cardiologist", "phone": "9876543210" }
    @PostMapping
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        Doctor saved = doctorService.addDoctor(doctor);
        return new ResponseEntity<>(saved, HttpStatus.CREATED); // 201
    }

    // GET /api/doctors → returns all doctors
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }

    // GET /api/doctors/1 → returns doctor with id 1
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return new ResponseEntity<>(doctorService.getDoctorById(id), HttpStatus.OK);
    }

    // GET /api/doctors/specialization/Cardiologist → all cardiologists
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable String specialization) {
        return new ResponseEntity<>(doctorService.getDoctorsBySpecialization(specialization), HttpStatus.OK);
    }

    // PUT /api/doctors/1
    // Body: updated doctor data
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctorService.updateDoctor(id, doctor), HttpStatus.OK);
    }

    // DELETE /api/doctors/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>("Doctor deleted successfully", HttpStatus.OK);
    }
}
