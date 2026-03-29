package com.hiten.docflow.repository;

import com.hiten.docflow.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Doctor Repository (Floor 1 - The Filing Clerk)
 * 
 * Same pattern as PatientRepository.
 * JpaRepository gives us save(), findAll(), findById(), deleteById() for free.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Spring magic: just by naming it "existsByPhone",
    // Spring generates: SELECT COUNT(*) FROM doctors WHERE phone = ?
    boolean existsByPhone(String phone);

    // Find doctors by specialization (e.g. "Cardiologist")
    // Spring generates: SELECT * FROM doctors WHERE specialization = ?
    java.util.List<Doctor> findBySpecialization(String specialization);
}
