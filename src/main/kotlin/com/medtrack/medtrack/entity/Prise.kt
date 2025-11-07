package com.medtrack.medtrack.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank


/**
 * représente une prise de médicament dans le système MedTrack.
 *
 * cette classe contient les informations relatives à une prise de médicament
 * effectuée par un patient à une date et une heure précises. chaque prise est
 * liée à un médicament et à un patient.
 *
 * @property id identifiant unique de la prise (généré automatiquement).
 * @property date date à laquelle la prise doit être effectuée.
 * @property heure heure à laquelle la prise doit être effectuée.
 * @property etat indique si la prise a été effectuée (true) ou non (false).
 * @property medicament médicament concerné par la prise (relation ManyToOne).
 * @property patient patient ayant effectué la prise (relation ManyToOne).
 */
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