package com.medtrack.medtrack.service

import com.medtrack.medtrack.entity.Medicament
import com.medtrack.medtrack.repository.MedicamentRepository
import org.springframework.stereotype.Service
import com.medtrack.medtrack.exception.ResourceNotFoundException

/**
 * Service pour la gestion des médicaments dans le système MedTrack.
 *
 * cette classe contient les opérations métier liées aux médicaments, telles que
 * l'ajout, la modification, la suppression et la recherche de médicaments.
 * elle communique avec le repository [MedicamentRepository] pour interagir
 * avec la base de données.
 *
 * Fonctions principales :
 * - ajouterMedicament : ajouter un nouveau médicament
 * - afficherTousLesMedicaments : récupérer la liste de tous les médicaments
 * - chercherMedicamentParId : rechercher un médicament par son identifiant
 * - modifierMedicament : modifier les informations d'un médicament existant
 * - supprimerMedicament : supprimer un médicament par son identifiant
 */


@Service
class MedicamentService(private val medicamentRepository: MedicamentRepository) {


    /**
     * ajoute un nouveau médicament dans la base de données.
     *
     * @param med objet médicament à ajouter
     * @return le médicament ajouté avec son identifiant généré
     */

    fun ajouterMedicament(med: Medicament): Medicament {
        return medicamentRepository.save(med)
    }


    /**
     * récupère la liste de tous les médicaments.
     *
     * @return liste de tous les médicaments enregistrés
     */

    fun afficherTousLesMedicaments(): List<Medicament> {
        return medicamentRepository.findAll()
    }


    /**
     * recherche un médicament par son identifiant.
     *
     * @param id identifiant du médicament
     * @return le médicament correspondant
     * @throws ResourceNotFoundException si le medicament n'existe pas
     */

    fun chercherMedicamentParId(id: Long): Medicament {
        return medicamentRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Médicament avec ID $id introuvable") }
    }


    /**
     * modifie un médicament existant dans la base de données.
     *
     * @param med objet médicament contenant les nouvelles informations
     * @return le médicament modifié
     */

    fun modifierMedicament(med: Medicament): Medicament {
        return medicamentRepository.save(med)
    }

    /**
     * supprime un médicament par son identifiant.
     *
     * @param id identifiant du médicament à supprimer
     */

    fun supprimerMedicament(id: Long) {
        medicamentRepository.deleteById(id)
    }
}