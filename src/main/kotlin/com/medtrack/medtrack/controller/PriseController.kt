package com.medtrack.medtrack.controller

import com.medtrack.medtrack.entity.Prise
import com.medtrack.medtrack.entity.PriseDTO
import com.medtrack.medtrack.service.PriseService
import com.medtrack.medtrack.repository.MedicamentRepository
import com.medtrack.medtrack.repository.PriseRepository
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import com.medtrack.medtrack.exception.ResourceNotFoundException


/**
 * Contrôleur REST pour la gestion des prises de médicaments dans le système MedTrack.
 *
 * cette classe expose les endpoints permettant de créer, lire, modifier et
 * supprimer des prises, ainsi que de mettre à jour leur état. elle communique
 * avec le service [PriseService] et les repositories [PriseRepository] et
 * [MedicamentRepository] pour effectuer les opérations métier.
 *
 * Endpoints disponibles :
 * - POST /prises/add : ajouter une nouvelle prise pour un médicament
 * - GET /prises/all : récupérer la liste de toutes les prises
 * - GET /prises/{id} : récupérer une prise par son identifiant
 * - PUT /prises/update : modifier une prise existante
 * - DELETE /prises/delete/{id} : supprimer une prise
 * - PUT /prises/updateEtat : mettre à jour l'état (effectuée/non effectuée) d'une prise
 * - GET /prises/oubliees/{patientId}/{medicamentId} : récupérer les prises oubliées pour un patient et un médicament
 */



@RestController
@RequestMapping("/prises")
class PriseController(private val priseService: PriseService,
                            private val priseRepository: PriseRepository,
                            private val medicamentRepository: MedicamentRepository) {


//    @PostMapping("/add")
//    fun ajouterPrise(@RequestBody prise: Prise): Prise {
//        return priseService.ajouterPrise(prise)
//    }
    /**
     * ajoute une nouvelle prise pour un médicament existant.
     *
     * @param dto objet DTO contenant les informations de la prise et l'id du médicament
     * @return message de confirmation
     * @throws ResourceNotFoundException si le médicament n'existe pas
     */
    @PostMapping("/add")
    fun ajouterPrise(@Valid @RequestBody dto: PriseDTO): String {

        val medicament = medicamentRepository.findById(dto.medicamentId)
            .orElseThrow {ResourceNotFoundException("Aucun médicament trouvé avec l'ID ${dto.medicamentId}")}

        val prise = Prise(
            heure = dto.heure,
            date = dto.date,
            medicament = medicament,
            patient = medicament.patient
        )

        priseRepository.save(prise)

        return "Prise ajoutée pour le médicament '${medicament.nom}' du patient '${medicament.patient?.nom} ${medicament.patient?.prenom}'."
    }


    /**
     * récupère la liste de toutes les prises.
     *
     * @return liste de toutes les prises enregistrées
     */

    @GetMapping("/all")
    fun afficherToutesLesPrises(): List<Prise> {
        return priseService.afficherToutesLesPrises()
    }

    /**
     * récupère une prise par son identifiant.
     *
     * @param id identifiant de la prise
     * @return la prise correspondante ou une exception
     */

    @GetMapping("/{id}")
    fun chercherPrise(@PathVariable id: Long): Prise {
        return priseService.chercherPriseParId(id)
    }

    /**
     * modifie une prise existante.
     *
     * @param prise objet prise avec les nouvelles informations
     * @return la prise modifiée
     */

    @PutMapping("/update")
    fun modifierPrise(@Valid@RequestBody prise: Prise): Prise {
        return priseService.modifierPrise(prise)
    }

    /**
     * supprime une prise par son identifiant.
     *
     * @param id identifiant de la prise à supprimer
     */

    @DeleteMapping("/delete/{id}")
    fun supprimerPrise(@PathVariable id: Long) {
        priseService.supprimerPrise(id)
    }

    /**
     * met à jour l'état (effectuée/non effectuée) d'une prise.
     *
     * @param priseId identifiant de la prise
     * @param etat nouvel état de la prise
     * @return message indiquant l'état mis à jour
     * @throws ResourceNotFoundException si la prise n'existe pas
     */

    @PutMapping("/updateEtat")
    fun updateEtatPrise(
        @RequestParam priseId: Long,
        @RequestParam etat: Boolean
    ): String {

        val prise = priseRepository.findById(priseId)
            .orElseThrow {ResourceNotFoundException("Aucune prise trouvée avec l'ID $priseId")}

        prise.etat = etat
        priseRepository.save(prise)

        val etatTexte = if (etat) "effectuée" else "non effectuée"

        return "La prise du ${prise.date} à ${prise.heure} pour le médicament '${prise.medicament?.nom}' a été marquée comme $etatTexte."
    }

    /**
     * récupère les prises oubliées pour un patient et un médicament spécifiques.
     *
     * @param patientId identifiant du patient
     * @param medicamentId identifiant du médicament
     * @return chaîne descriptive des prises oubliées
     */

    @GetMapping("/oubliees/{patientId}/{medicamentId}")
    fun getPrisesOublieesParMedicament(@PathVariable patientId: Long, @PathVariable medicamentId: Long
    ): String {
        return priseService.prisesOublieesParMedicament(patientId, medicamentId)
    }
}
