package com.medtrack.medtrack.repository


import com.medtrack.medtrack.entity.Prise
import org.springframework.data.jpa.repository.JpaRepository


interface PriseRepository : JpaRepository<Prise, Long>