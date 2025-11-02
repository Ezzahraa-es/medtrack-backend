package com.medtrack.medtrack.repository


import com.medtrack.medtrack.entity.Medicament
import org.springframework.data.jpa.repository.JpaRepository


interface MedicamentRepository : JpaRepository<Medicament, Long>