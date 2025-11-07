package com.medtrack.medtrack.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

/**
 * représente un medecin dans le systme MedTrack.
 *
 * cette classe contient toutes les informations professionnelles
 * d'un médecin enregistré dans la plateforme de suivi médical.
 * @property id identifiant unique du médecin.
 * @property nom nom du médecin.
 * @property prenom prénom du médecin.
 * @property specialite specialite medicale du medecin.
 * @property patients liste des patients suivis par ce médecin (relation OneToMany)
 *
 */

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