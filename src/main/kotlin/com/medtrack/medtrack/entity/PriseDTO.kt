package com.medtrack.medtrack.entity


/**
 * représente un objet de transfert de données (DTO) pour une prise de médicament
 * dans le système MedTrack.
 *
 * cette classe est utilisée pour transférer les informations d'une prise
 * entre les différentes couches de l'application (par exemple entre le frontend et le backend).
 *
 * @property heure heure à laquelle la prise doit être effectuée.
 * @property date date de la prise.
 * @property medicamentId identifiant du médicament associé à cette prise.
 */
data class PriseDTO(
    val heure: String,
    val date: String,
    val medicamentId: Long
)