package com.medtrack.medtrack.service

import com.medtrack.medtrack.entity.Prise
import com.medtrack.medtrack.repository.PatientRepository
import com.medtrack.medtrack.repository.PriseRepository
import org.springframework.stereotype.Service
import com.medtrack.medtrack.exception.ResourceNotFoundException

/**
 * Service pour la gestion des prises de médicaments dans le système MedTrack.
 *
 * cette classe contient les opérations métier liées aux prises, telles que
 * l'ajout, la modification, la suppression, la recherche et la récupération
 * des prises oubliées pour un patient et un médicament spécifiques.
 * elle communique avec les repositories [PriseRepository] et [PatientRepository]
 * pour interagir avec la base de données.
 *
 * Fonctions principales :
 * - ajouterPrise : ajouter une nouvelle prise
 * - afficherToutesLesPrises : récupérer la liste de toutes les prises
 * - chercherPriseParId : rechercher une prise par son identifiant
 * - modifierPrise : modifier les informations d'une prise existante
 * - supprimerPrise : supprimer une prise par son identifiant
 * - prisesOublieesParMedicament : récupérer les prises oubliées pour un patient et un médicament
 */
@Service
class PriseService(
    private val priseRepository: PriseRepository,
    private val patientRepository: PatientRepository
) {

    /**
     * ajoute une nouvelle prise dans la base de données.
     *
     * @param prise objet prise à ajouter
     * @return la prise ajoutée avec son identifiant généré
     */
    fun ajouterPrise(prise: Prise): Prise {
        return priseRepository.save(prise)
    }

    /**
     * récupère la liste de toutes les prises.
     *
     * @return liste de toutes les prises enregistrées
     */
    fun afficherToutesLesPrises(): List<Prise> {
        return priseRepository.findAll()
    }

    /**
     * recherche une prise par son identifiant.
     *
     * @param id identifiant de la prise
     * @return la prise correspondante
     * @throws ResourceNotFoundException si la prise n’existe pas
     */
    fun chercherPriseParId(id: Long): Prise {
        return priseRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Prise avec ID $id introuvable") }
    }

    /**
     * modifie une prise existante dans la base de données.
     *
     * @param prise objet prise contenant les nouvelles informations
     * @return la prise modifiée
     */
    fun modifierPrise(prise: Prise): Prise {
        return priseRepository.save(prise)
    }

    /**
     * supprime une prise par son identifiant.
     *
     * @param id identifiant de la prise à supprimer
     */
    fun supprimerPrise(id: Long) {
        priseRepository.deleteById(id)
    }

    /**
     * récupère les prises oubliées pour un patient et un médicament spécifiques.
     *
     * @param patientId identifiant du patient
     * @param medicamentId identifiant du médicament
     * @return chaîne descriptive des prises oubliées
     * @throws ResourceNotFoundException si le patient introuvable
     */
    fun prisesOublieesParMedicament(patientId: Long, medicamentId: Long): String {
        val patient = patientRepository.findById(patientId)
            .orElseThrow { ResourceNotFoundException("le patient introuvable") }

        val toutesLesPrises = priseRepository.findAll()
        val prisesOubliees = toutesLesPrises.filter {
            it.patient?.id == patientId && it.medicament?.id == medicamentId && it.etat == false
        }

        if (prisesOubliees.isEmpty()) {
            return "Le patient ${patient.nom} ${patient.prenom} n’a oublié aucune prise pour ce médicament."
        }

        var texte = "Le patient ${patient.nom} ${patient.prenom} a oublié ${prisesOubliees.size} prise(s) " +
                "du médicament ${prisesOubliees[0].medicament?.nom ?: "inconnu"}.\n\n"
        texte += "Détails des prises oubliées :\n"
        prisesOubliees.forEach { prise ->
            texte += "- Date : ${prise.date}, Heure : ${prise.heure}\n"
        }

        return texte
    }
}
