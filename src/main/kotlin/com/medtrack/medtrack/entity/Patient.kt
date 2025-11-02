package com.medtrack.medtrack.entity

import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
class Patient(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Le nom ne doit pas être vide")
    val nom: String,

    @field:NotBlank(message = "Le prénom ne doit pas être vide")
    val prenom: String,

    @field:Min(value = 1, message = "L'âge doit être supérieur à 0")
    @field:Max(value = 120, message = "L'âge ne doit pas dépasser 120 ans")
    val age: Int,

    @field:NotBlank(message = "La maladie est obligatoire")
    val maladie: String,

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    val medecin: Medecin? = null,

    @OneToMany(mappedBy = "patient", cascade = [CascadeType.ALL], orphanRemoval = true)
    var medicaments: MutableList<Medicament> = mutableListOf(),

    @OneToMany(mappedBy = "patient", cascade = [CascadeType.ALL], orphanRemoval = true)
    var prises: MutableList<Prise> = mutableListOf()
)