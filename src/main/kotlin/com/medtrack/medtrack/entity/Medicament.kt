package com.medtrack.medtrack.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

/**
 * représente un médicament dans le système MedTrack.
 *
 * cette classe contient toutes les informations relatives à un médicament
 * prescrit à un patient, incluant son nom, sa dose, et sa fréquence d'administration.
 *
 * @property id identifiant unique du médicament.
 * @property nom nom du médicament (ne doit pas être vide).
 * @property dose dose prescrite du médicament.
 * @property frequence fréquence d'administration du médicament.
 * @property patient le patient auquel ce médicament est associé (relation ManyToOne).
 * @property prises liste des prises de ce médicament effectuées par le patient (relation OneToMany).
 */
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