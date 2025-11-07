package com.medtrack.medtrack.entity

/**
 * Représente les données nécessaires pour créer un nouveau patient.
 *
 * Ce DTO permet de recevoir uniquement les informations utiles depuis l'API,
 * sans exposer directement l'entité Patient.
 *
 * @property nom nom du patient
 * @property prenom prénom du patient
 * @property age âge du patient
 * @property maladie maladie diagnostiquée
 * @property medecinId identifiant du médecin auquel le patient sera associé
 */
data class PatientDTO(
    val nom: String,
    val prenom: String,
    val age: Int,
    val maladie: String,
    val medecinId: Long
)