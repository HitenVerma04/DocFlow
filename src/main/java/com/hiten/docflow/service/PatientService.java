package com.hiten.docflow.service;

import com.hiten.docflow.entity.Patient;
import com.hiten.docflow.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.hiten.docflow.exception.ResourceNotFoundException;
import com.hiten.docflow.exception.DuplicateResourceException;

/**
 * Patient Service (Floor 2 - The Manager)
 * 
 * Contains all the "business logic". The Controller (Floor 3) talks to the Service,
 * and the Service talks to the Repository (Floor 1).
 */
@Service
public class PatientService {

    // Dependency Injection: Spring automatically provides a PatientRepository object
    @Autowired
    private PatientRepository patientRepository;

    // CREATE
    public Patient addPatient(Patient patient) {
        // Business logic: Check if phone number already exists
        if (patientRepository.existsByPhone(patient.getPhone())) {
            throw new DuplicateResourceException("Patient with this phone number already exists!");
        }
        return patientRepository.save(patient); // Saves to database
    }

    // READ ALL
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // READ BY ID
    public Patient getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            return patient.get();
        } else {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
    }

    // UPDATE
    public Patient updatePatient(Long id, Patient updatedPatientData) {
        // 1. Find existing patient
        Patient existingPatient = getPatientById(id);
        
        // 2. Update their fields
        existingPatient.setName(updatedPatientData.getName());
        existingPatient.setAge(updatedPatientData.getAge());
        existingPatient.setGender(updatedPatientData.getGender());
        existingPatient.setPhone(updatedPatientData.getPhone());
        existingPatient.setAddress(updatedPatientData.getAddress());
        
        // 3. Save the updated patient back to DB
        return patientRepository.save(existingPatient);
    }

    // DELETE
    public void deletePatient(Long id) {
        // Find them first to ensure they exist
        Patient patient = getPatientById(id);
        patientRepository.delete(patient);
    }
}
