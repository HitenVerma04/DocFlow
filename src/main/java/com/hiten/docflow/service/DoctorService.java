package com.hiten.docflow.service;

import com.hiten.docflow.entity.Doctor;
import com.hiten.docflow.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.hiten.docflow.exception.ResourceNotFoundException;
import com.hiten.docflow.exception.DuplicateResourceException;

/**
 * Doctor Service (Floor 2 - The Manager)
 * 
 * Contains all the business logic for doctors.
 * Controller talks to Service → Service talks to Repository.
 */
@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // CREATE - Add a new doctor
    public Doctor addDoctor(Doctor doctor) {
        if (doctorRepository.existsByPhone(doctor.getPhone())) {
            throw new DuplicateResourceException("Doctor with this phone number already exists!");
        }
        return doctorRepository.save(doctor);
    }

    // READ ALL - Get every doctor
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // READ BY ID - Get one doctor
    public Doctor getDoctorById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isPresent()) {
            return doctor.get();
        } else {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
    }

    // READ BY SPECIALIZATION - e.g. get all "Cardiologist" doctors
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    // UPDATE - Change a doctor's details
    public Doctor updateDoctor(Long id, Doctor updatedData) {
        Doctor existingDoctor = getDoctorById(id);

        existingDoctor.setName(updatedData.getName());
        existingDoctor.setSpecialization(updatedData.getSpecialization());
        existingDoctor.setPhone(updatedData.getPhone());

        return doctorRepository.save(existingDoctor);
    }

    // DELETE - Remove a doctor
    public void deleteDoctor(Long id) {
        Doctor doctor = getDoctorById(id);
        doctorRepository.delete(doctor);
    }
}
