package com.medtrack.medtrack.service

import com.medtrack.medtrack.entity.Medecin
import com.medtrack.medtrack.exception.ResourceNotFoundException
import com.medtrack.medtrack.repository.MedecinRepository
import com.medtrack.medtrack.repository.PatientRepository

import org.springframework.stereotype.Service

/**
 * Service pour la gestion des médecins dans le système MedTrack.
 *
 * cette classe contient les opérations métier liées aux médecins, telles que
 * l'ajout, la modification, la suppression et la recherche de médecins.
 * elle communique avec les repositories [MedecinRepository] et [PatientRepository]
 * pour interagir avec la base de données.
 *
 * Fonctions principales :
 * - ajouterMedecin : ajouter un nouveau médecin
 * - afficherTousLesMedecins : récupérer la liste de tous les médecins
 * - chercherMedecinParId : rechercher un médecin par son identifiant
 * - modifierMedecin : modifier les informations d'un médecin existant
 * - supprimerMedecin : supprimer un médecin par son identifiant
 */

@Service
class MedecinService(private val medecinRepository: MedecinRepository ,private val patientRepository: PatientRepository) {

    /**
     * ajoute un nouveau médecin dans la base de données.
     *
     * @param medecin objet médecin à ajouter
     * @return le médecin ajouté avec son identifiant généré
     */

    fun ajouterMedecin(medecin: Medecin): Medecin {
        return medecinRepository.save(medecin)
    }

    /**
     * récupère la liste de tous les médecins.
     *
     * @return liste de tous les médecins enregistrés
     */

    fun afficherTousLesMedecins(): List<Medecin> {
        return medecinRepository.findAll()
    }

    /**
     * recherche un médecin par son identifiant.
     *
     * @param id identifiant du médecin
     * @return le médecin correspondant
     * @throws ResourceNotFoundException si le medecin n'existe pas
     */

    fun chercherMedecinParId(id: Long): Medecin {
        return medecinRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Médecin avec ID $id introuvable") }
    }

    /**
     * modifie un médecin existant dans la base de données.
     *
     * @param medecin objet médecin contenant les nouvelles informations
     * @return le médecin modifié
     */

    fun modifierMedecin(medecin: Medecin): Medecin {
        return medecinRepository.save(medecin)
    }

    /**
     * supprime un médecin par son identifiant.
     *
     * @param id identifiant du médecin à supprimer
     */

    fun supprimerMedecin(id: Long) {
        medecinRepository.deleteById(id)
    }
    
}