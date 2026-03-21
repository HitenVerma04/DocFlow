package com.hiten.docflow.repository;

import com.hiten.docflow.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Patient Repository (Floor 1 - The Filing Clerk)
 * 
 * JpaRepository gives us built-in methods like:
 * save(), findAll(), findById(), deleteById()
 * We don't have to write ANY SQL code!
 * 
 * <Patient, Long> means: "This repository is for the Patient entity, 
 * and its primary key (ID) is of type Long"
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    // Spring Data JPA magic: Just by naming the method "findByPhone", 
    // Spring knows you want to search by the phone column!
    boolean existsByPhone(String phone);
}
