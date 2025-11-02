package com.medtrack.medtrack.service

import com.medtrack.medtrack.entity.Medicament
import com.medtrack.medtrack.repository.MedicamentRepository
import org.springframework.stereotype.Service

@Service
class MedicamentService(private val medicamentRepository: MedicamentRepository) {

    fun ajouterMedicament(med: Medicament): Medicament {
        return medicamentRepository.save(med)
    }

    fun afficherTousLesMedicaments(): List<Medicament> {
        return medicamentRepository.findAll()
    }

    fun chercherMedicamentParId(id: Long): Medicament? {
        return medicamentRepository.findById(id).orElse(null)
    }

    fun modifierMedicament(med: Medicament): Medicament {
        return medicamentRepository.save(med)
    }

    fun supprimerMedicament(id: Long) {
        medicamentRepository.deleteById(id)
    }
}