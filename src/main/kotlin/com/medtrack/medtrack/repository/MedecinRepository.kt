package com.medtrack.medtrack.repository

import com.medtrack.medtrack.entity.Medecin
import org.springframework.data.jpa.repository.JpaRepository


interface MedecinRepository : JpaRepository<Medecin, Long>