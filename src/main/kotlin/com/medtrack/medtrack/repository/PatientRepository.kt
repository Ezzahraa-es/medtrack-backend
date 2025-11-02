package com.medtrack.medtrack.repository


import com.medtrack.medtrack.entity.Patient
import org.springframework.data.jpa.repository.JpaRepository


interface PatientRepository: JpaRepository<Patient, Long>