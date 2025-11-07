package com.medtrack.medtrack.controller

import com.medtrack.medtrack.entity.Medicament
import com.medtrack.medtrack.entity.MedicamentDTO
import com.medtrack.medtrack.service.MedicamentService
import com.medtrack.medtrack.repository.PatientRepository
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import com.medtrack.medtrack.exception.ResourceNotFoundException

/**
 * Contrôleur REST pour la gestion des medicaments dans le système MedTrack.
 *
 * cette classe expose les endpoints permettant de créer, lire, modifier et
 * supprimer des medicaments. elle communique avec le service [MedicamentService]
 * pour effectuer les opérations métier.
 *
 * Endpoints disponibles :
 * - POST /medicaments/add : ajouter un nouveau médecin
 * - GET /medicaments/all : récupérer la liste de tous les medicament
 * - GET /medicaments/{id} : récupérer un medicament par son identifiant
 * - PUT /medicaments/update : modifier un medicament existant
 * - DELETE /medicaments/delete/{id} : supprimer un medicament
 *
 */
@RestController
@RequestMapping("/medicaments")
class MedicamentController(private val medicamentService: MedicamentService, private val patientRepository: PatientRepository) {


    /**
     * ajoute un médicament à un patient existant.
     *
     * @param medDTO objet DTO contenant les informations du médicament et l'id du patient
     * @return message de confirmation
     * @throws ResourceNotFoundException si le patient n'existe pas
     */

//    @PostMapping("/add")
//    fun ajouterMedicament(@RequestBody med: Medicament): Medicament {
//        return medicamentService.ajouterMedicament(med)
//    }
    @PostMapping("/add")
    fun ajouterMedicament(@Valid @RequestBody medDTO: MedicamentDTO): String {

        val patient = patientRepository.findById(medDTO.patientId)
            .orElseThrow {  ResourceNotFoundException("Aucun patient trouvé avec l'ID ${medDTO.patientId}")}

        val medicament = Medicament(
            nom = medDTO.nom,
            dose = medDTO.dose,
            frequence = medDTO.frequence,
            patient = patient
        )

        medicamentService.ajouterMedicament(medicament)

        return "Le médicament '${medicament.nom}' a été ajouté pour le patient '${patient.nom} ${patient.prenom}'."
    }


    /**
     * récupère la liste de tous les médicaments.
     *
     * @return liste de tous les médicaments
     */

    @GetMapping("/all")
    fun afficherTousLesMedicaments(): List<Medicament> {
        return medicamentService.afficherTousLesMedicaments()
    }

    /**
     * récupère un médicament par son identifiant.
     *
     * @param id identifiant du médicament
     * @return le médicament correspondant ou une exception
     */

    @GetMapping("/{id}")
    fun chercherMedicament(@PathVariable id: Long): Medicament {
        return medicamentService.chercherMedicamentParId(id)
    }

    /**
     * modifie un médicament existant.
     *
     * @param med objet médicament avec les nouvelles informations
     * @return le médicament modifié
     */

    @PutMapping("/update")
    fun modifierMedicament(@Valid @RequestBody med: Medicament): Medicament {
        return medicamentService.modifierMedicament(med)
    }


    /**
     * supprime un médicament par son identifiant.
     *
     * @param id identifiant du médicament à supprimer
     */

    @DeleteMapping("/delete/{id}")
    fun supprimerMedicament(@PathVariable id: Long) {
        medicamentService.supprimerMedicament(id)
    }
}
