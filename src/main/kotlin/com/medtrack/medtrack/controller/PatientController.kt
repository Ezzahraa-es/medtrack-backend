package com.medtrack.medtrack.controller

import com.medtrack.medtrack.entity.Patient
import com.medtrack.medtrack.service.PatientService
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import com.medtrack.medtrack.entity.PatientDTO


/**
 * Contrôleur REST pour la gestion des patients dans le système MedTrack.
 *
 * cette classe expose les endpoints permettant de créer, lire, modifier et
 * supprimer des patients. elle communique avec le service [PatientService]
 * pour effectuer les opérations métier.
 *
 * Endpoints disponibles :
 * - POST /patients/add : ajouter un nouveau patient
 * - GET /patients/all : récupérer la liste de tous les patients
 * - GET /patients/{id} : récupérer un patient par son identifiant
 * - PUT /patients/update : modifier un patient existant
 * - DELETE /patients/delete/{id} : supprimer un patient
 * - GET /patients/{id}/dossier : consulter le dossier complet d’un patient
 */


@RestController
@RequestMapping("/patients")
class PatientController(private val patientService: PatientService) {

    /**
     * endpoint permettant d'afficher une liste paginée de patients
     * sous forme de texte, triée selon un champ spécifique.
     *
     * exemple d'appel :
     * GET /patients/paged-text?page=0&size=5&sortBy=nom
     *
     * @param page numéro de la page (0 par défaut)
     * @param size taille de la page (5 par défaut)
     * @param sortBy champ pour le tri ("nom" par défaut)
     * @return chaîne descriptive de la page de patients
     */
    @GetMapping("/paged")
fun afficherPagedPatients(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int,
        @RequestParam(defaultValue = "nom") sortBy: String
        ): String {
    return patientService.afficherPagePatients(page, size, sortBy)
    }



    /**
     * Endpoint permettant d'ajouter un nouveau patient.
     *
     * Reçoit un PatientDTO en JSON, transmet les données au service,
     * puis retourne un message textuel confirmant l'ajout.
     *
     * @param dto données envoyées depuis Postman pour créer un patient
     * @return message textuel indiquant le résultat de la création
     */
    @PostMapping("/add")
    fun ajouterPatient(@RequestBody dto: PatientDTO): String {
        return patientService.ajouterPatient(dto)
    }

    /**
     * 🔍 Affiche la liste complète des patients sous forme de texte.
     *
     * Cette méthode permet d'obtenir une vue d'ensemble des patients enregistrés dans le système.
     * Pour chaque patient, elle affiche ses informations personnelles ainsi que la liste
     * de ses médicaments (nom, dose, fréquence).
     *
     * L'affichage est textuel afin d'éviter les problèmes de boucles infinies liés
     * aux relations bidirectionnelles lors du retour en JSON.
     *
     * @return une chaîne de texte contenant les détails de tous les patients,
     * ou un message indiquant qu'aucun patient n’est enregistré.
     */

    @GetMapping("/all")
    fun afficherTousLesPatients(): String {
        val patients = patientService.afficherTousLesPatients()

        if (patients.isEmpty()) {
            return "Aucun patient trouvé dans la base de données."
        }
        var resultat = "Liste des patients enregistrés :\n"
        for (patient in patients) {
            resultat += "Nom etprenom: ${patient.nom} ${patient.prenom}\n"
            resultat += "Âge : ${patient.age} \nMaladie : ${patient.maladie}\n"

            if (patient.medicaments.isNotEmpty()) {
                resultat += "Médicaments :\n"
                for (med in patient.medicaments) {
                    resultat += "- ${med.nom} (${med.dose}, ${med.frequence})\n"
                }
            } else {
                resultat += "Aucun médicament enregistré.\n"
            }

            resultat += "\n------------------------------\n\n"
        }

        return resultat
    }

    /**
     * récupère un patient par son identifiant.
     *
     * @param id identifiant du patient
     * @return le patient correspondant ou une exception
     */

    @GetMapping("/{id}")
    fun chercherPatient(@PathVariable id: Long): Patient {
        return patientService.chercherPatientParId(id)
    }

    /**
     * modifie un patient existant.
     *
     * @param patient objet patient avec les nouvelles informations
     * @return le patient modifié
     */

    @PutMapping("/update")
    fun modifierPatient(@Valid@RequestBody patient: Patient): Patient {
        return patientService.modifierPatient(patient)
    }

    /**
     * supprime un patient par son identifiant.
     *
     * @param id identifiant du patient à supprimer
     */

    @DeleteMapping("/delete/{id}")
    fun supprimerPatient(@PathVariable id: Long) {
        patientService.supprimerPatient(id)
    }

    /**
     * récupère le dossier complet d’un patient, incluant ses médicaments et prises.
     *
     * @param id identifiant du patient
     * @return chaîne descriptive du dossier complet du patient
     */

    @GetMapping("/{id}/dossier")
    fun getDossierComplet(@PathVariable id: Long): String {
        return patientService.consulterDossierComplet(id)
    }
}
