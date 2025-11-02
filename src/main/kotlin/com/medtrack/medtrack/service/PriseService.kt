package com.medtrack.medtrack.service

import com.medtrack.medtrack.entity.Prise
import com.medtrack.medtrack.repository.PatientRepository
import com.medtrack.medtrack.repository.PriseRepository
import org.springframework.stereotype.Service

@Service
class PriseService(private val priseRepository: PriseRepository, private val patientRepository: PatientRepository) {

    fun ajouterPrise(prise: Prise): Prise {
        return priseRepository.save(prise)
    }

    fun afficherToutesLesPrises(): List<Prise> {
        return priseRepository.findAll()
    }

    fun chercherPriseParId(id: Long): Prise? {
        return priseRepository.findById(id).orElse(null)
    }

    fun modifierPrise(prise: Prise): Prise {
        return priseRepository.save(prise)
    }

    fun supprimerPrise(id: Long) {
        priseRepository.deleteById(id)
    }

    fun prisesOublieesParMedicament(patientId: Long, medicamentId: Long): String {
        val patient = patientRepository.findById(patientId).orElse(null)
        if (patient == null) {
            return "Aucun patient trouvé avec l'ID $patientId."
        }
        val toutesLesPrises = priseRepository.findAll()
        val prisesOubliees = mutableListOf<Prise>()

        for (prise in toutesLesPrises) {
            if (prise.patient?.id == patientId &&
                prise.medicament?.id == medicamentId &&
                prise.etat == false) {
                prisesOubliees.add(prise)
            }
        }
        if (prisesOubliees.isEmpty()) {
            return "Le patient ${patient.nom} ${patient.prenom} n’a oublié aucune prise pour ce médicament."
        }

        var texte = "Le patient ${patient.nom} ${patient.prenom} a oublié ${prisesOubliees.size} prise(s) " +
                "du médicament ${prisesOubliees[0].medicament?.nom ?: "inconnu"}.\n\n"
        texte += "Détails des prises oubliées :\n"
        for (prise in prisesOubliees) {
            texte += "- Date : ${prise.date}, Heure : ${prise.heure}\n"
        }

        return texte
    }


}