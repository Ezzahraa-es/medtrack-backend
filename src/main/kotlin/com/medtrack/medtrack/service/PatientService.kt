package com.medtrack.medtrack.service

import com.medtrack.medtrack.entity.Patient
import com.medtrack.medtrack.repository.MedicamentRepository
import com.medtrack.medtrack.repository.PatientRepository
import com.medtrack.medtrack.repository.PriseRepository
import org.springframework.stereotype.Service

@Service
class PatientService( private val patientRepository: PatientRepository ,
                            private val medicamentRepository: MedicamentRepository ,
                            private val priseRepository: PriseRepository) {

    fun ajouterPatient(patient: Patient): Patient {
        return patientRepository.save(patient)
    }

    fun afficherTousLesPatients(): List<Patient> {
        return patientRepository.findAll()
    }

    fun chercherPatientParId(id: Long): Patient? {
        return patientRepository.findById(id).orElse(null)
    }

    fun modifierPatient(patient: Patient): Patient {
        return patientRepository.save(patient)
    }

    fun supprimerPatient(id: Long) {
        patientRepository.deleteById(id)
    }

    fun consulterDossierComplet(patientId: Long): String {
        val patient = patientRepository.findById(patientId).orElse(null)

        if (patient == null) {
            return "Aucun patient trouvé avec l'ID $patientId"
        }

        val medicamentsPatient = medicamentRepository.findAll().filter { it.patient?.id == patientId }

        val prisesPatient = priseRepository.findAll().filter { it.patient?.id == patientId }

        var prisesEffectuees = 0
        var prisesOubliees = 0

        for (prise in prisesPatient) {
            if (prise.etat == true) {
                prisesEffectuees++
            } else if (prise.etat == false) {
                prisesOubliees++
            }
        }

        val resultat = """
        Dossier du patient :
        Nom : ${patient.nom}
        Prénom : ${patient.prenom}
        Maladie : ${patient.maladie}
        Âge : ${patient.age}

        Médicaments :
        ${medicamentsPatient.joinToString("\n") { "- ${it.nom} (${it.dose}, ${it.frequence})" }}

        Prises enregistrées : ${prisesPatient.size}
         Effectuées : $prisesEffectuees
         Oubliées : $prisesOubliees
    """.trimIndent()

        return resultat
    }

}