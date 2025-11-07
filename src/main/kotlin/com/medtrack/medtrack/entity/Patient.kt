package com.medtrack.medtrack.entity

import jakarta.persistence.*
import jakarta.validation.constraints.*


/**
 * représente un patient dans le système MedTrack.
 *
 * cette classe contient toutes les informations personnelles et médicales
 * d'un patient suivi dans la plateforme. chaque patient est associé à un médecin
 * et peut avoir plusieurs médicaments et prises liés à son traitement.
 *
 * @property id identifiant unique du patient (généré automatiquement).
 * @property nom nom du patient (ne doit pas être vide).
 * @property prenom prénom du patient (ne doit pas être vide).
 * @property age âge du patient (doit être compris entre 1 et 120 ans).
 * @property maladie maladie principale du patient.
 * @property medecin médecin responsable du suivi du patient (relation ManyToOne).
 * @property medicaments liste des médicaments prescrits au patient (relation OneToMany).
 * @property prises liste des prises effectuées par le patient (relation OneToMany).
 */

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