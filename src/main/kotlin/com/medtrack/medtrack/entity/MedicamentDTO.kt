package com.medtrack.medtrack.entity

data class MedicamentDTO(
    val nom: String,
    val dose: String,
    val frequence: String,
    val patientId: Long
)