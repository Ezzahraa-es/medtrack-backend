package com.medtrack.medtrack.service

import com.medtrack.medtrack.entity.Medecin
import com.medtrack.medtrack.entity.Patient
import com.medtrack.medtrack.repository.MedecinRepository
import com.medtrack.medtrack.repository.PatientRepository

import org.springframework.stereotype.Service

@Service
class MedecinService(private val medecinRepository: MedecinRepository ,private val patientRepository: PatientRepository) {

    fun ajouterMedecin(medecin: Medecin): Medecin {
        return medecinRepository.save(medecin)
    }

    fun afficherTousLesMedecins(): List<Medecin> {
        return medecinRepository.findAll()
    }

    fun chercherMedecinParId(id: Long): Medecin? {
        return medecinRepository.findById(id).orElse(null)
    }

    fun modifierMedecin(medecin: Medecin): Medecin {
        return medecinRepository.save(medecin)
    }

    fun supprimerMedecin(id: Long) {
        medecinRepository.deleteById(id)
    }
    
}