package com.medtrack.medtrack.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
class Prise(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "La date est obligatoire")
    val date: String,

    @field:NotBlank(message = "L'heure est obligatoire")
    val heure: String,


    var etat: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "medicament_id")
    val medicament: Medicament? = null,

    @ManyToOne
    @JoinColumn(name = "patient_id")
    var patient: Patient? = null
    )