package com.medtrack.medtrack.entity

/**
 * représente un objet de transfert de données (DTO) pour un médicament
 * dans le système MedTrack.
 *
 * cette classe est utilisée pour transférer les informations d'un médicament
 * entre les couches de l'application (par exemple entre le frontend et le backend).
 *
 * @property nom nom du médicament.
 * @property dose dose prescrite du médicament.
 * @property frequence fréquence d'administration du médicament.
 * @property patientId identifiant du patient auquel ce médicament est associé.
 */
data class MedicamentDTO(
    val nom: String,
    val dose: String,
    val frequence: String,
    val patientId: Long
)