package com.medtrack.medtrack.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank


@Entity
class Medicament(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @field:NotBlank(message = "Le nom du médicament ne doit pas être vide")
    var nom: String,

    @field:NotBlank(message = "La dose du médicament est obligatoire")
    var dose: String,

    @field:NotBlank(message = "La fréquence du médicament est obligatoire")
    var frequence: String,

    @ManyToOne
    @JoinColumn(name = "patient_id")
    var patient: Patient? = null,


    @OneToMany(mappedBy = "medicament", cascade = [CascadeType.ALL], orphanRemoval = true)
    var prises: MutableList<Prise> = mutableListOf()
    )