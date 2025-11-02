package com.medtrack.medtrack.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank


@Entity
class Medecin(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Le nom du médecin ne doit pas être vide")
    val nom: String,

    @field:NotBlank(message = "Le prénom du médecin ne doit pas être vide")
    val prenom: String,

    @field:NotBlank(message = "La spécialité du médecin est obligatoire")
    val specialite: String,


    @OneToMany(mappedBy = "medecin", cascade = [CascadeType.ALL], orphanRemoval = true)
    var patients: MutableList<Patient> = mutableListOf()
)