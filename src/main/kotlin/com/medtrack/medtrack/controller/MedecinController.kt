package com.medtrack.medtrack.controller

import com.medtrack.medtrack.entity.Medecin
import com.medtrack.medtrack.service.MedecinService
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

/**
 * Contrôleur REST pour la gestion des médecins dans le système MedTrack.
 *
 * cette classe expose les endpoints permettant de créer, lire, modifier et
 * supprimer des médecins. elle communique avec le service [MedecinService]
 * pour effectuer les opérations métier.
 *
 * Endpoints disponibles :
 * - POST /medecins/add : ajouter un nouveau médecin
 * - GET /medecins/all : récupérer la liste de tous les médecins
 * - GET /medecins/{id} : récupérer un médecin par son identifiant
 * - PUT /medecins/update : modifier un médecin existant
 * - DELETE /medecins/delete/{id} : supprimer un médecin
 *
 */
@RestController
@RequestMapping("/medecins")
class MedecinController(private val medecinService: MedecinService) {

    /**
     * ajoute un nouveau médecin dans le système.
     *
     * @param medecin objet médecin à ajouter (valide selon les contraintes de validation)
     * @return le médecin ajouté avec son identifiant généré
     */

    @PostMapping("/add")
    fun ajouterMedecin(@Valid@RequestBody medecin: Medecin): Medecin {
        return medecinService.ajouterMedecin(medecin)
    }

    /**
     * récupère la liste de tous les médecins enregistrés.
     *
     * @return liste de tous les médecins
     */

    @GetMapping("/all")
    fun afficherTousLesMedecins(): List<Medecin> {
        return medecinService.afficherTousLesMedecins()
    }

    /**
     * récupère un médecin par son identifiant.
     *
     * @param id identifiant du médecin à rechercher
     * @return le médecin correspondant ou une execption
     */

    @GetMapping("/{id}")
    fun chercherMedecin(@PathVariable id: Long): Medecin {
        return medecinService.chercherMedecinParId(id)
    }

    /**
     * modifie un médecin existant dans le système.
     *
     * @param medecin objet médecin contenant les nouvelles informations
     * @return le médecin modifié
     */

    @PutMapping("/update")
    fun modifierMedecin(@Valid@RequestBody medecin: Medecin): Medecin {
        return medecinService.modifierMedecin(medecin)
    }


    /**
     * supprime un médecin par son identifiant.
     *
     * @param id identifiant du médecin à supprimer
     */

    @DeleteMapping("/delete/{id}")
    fun supprimerMedecin(@PathVariable id: Long) {
        medecinService.supprimerMedecin(id)
    }

}
