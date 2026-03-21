package com.hiten.docflow.controller;

import com.hiten.docflow.entity.Patient;
import com.hiten.docflow.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Patient Controller (Floor 3 - The Receptionist)
 * 
 * Receives incoming HTTP requests (from Postman, React, etc.),
 * translates JSON into Java objects, and passes them to the Service.
 */
@RestController // Tells Spring this class handles REST API requests
@RequestMapping("/api/patients") // Base URL for all endpoints in this class
public class PatientController {

    @Autowired
    private PatientService patientService;

    // POST /api/patients - Creates a new patient
    // @RequestBody converts the incoming JSON into a Patient Java object
    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.addPatient(patient);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED); // 201 Created
    }

    // GET /api/patients - Get all patients
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK); // 200 OK
    }

    // GET /api/patients/1 - Get patient by ID 1
    // @PathVariable extracts the "1" from the URL and puts it into the 'id' variable
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    // PUT /api/patients/1 - Update patient by ID 1
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Patient updatedPatient = patientService.updatePatient(id, patient);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    // DELETE /api/patients/1 - Delete patient by ID 1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>("Patient deleted successfully", HttpStatus.OK);
    }
}
