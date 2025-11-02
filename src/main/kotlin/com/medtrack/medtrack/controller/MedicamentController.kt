package com.medtrack.medtrack.controller

import com.medtrack.medtrack.entity.Medicament
import com.medtrack.medtrack.entity.MedicamentDTO
import com.medtrack.medtrack.service.MedicamentService
import com.medtrack.medtrack.repository.PatientRepository
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid


@RestController
@RequestMapping("/medicaments")
class MedicamentController(private val medicamentService: MedicamentService, private val patientRepository: PatientRepository) {

//    @PostMapping("/add")
//    fun ajouterMedicament(@RequestBody med: Medicament): Medicament {
//        return medicamentService.ajouterMedicament(med)
//    }
    @PostMapping("/add")
    fun ajouterMedicament(@Valid@RequestBody medDTO: MedicamentDTO): String {
        val patient = patientRepository.findById(medDTO.patientId).orElse(null)
            ?: return "patient avec l'id ${medDTO.patientId} introuvable."

        val medicament = Medicament(
            nom = medDTO.nom,
            dose = medDTO.dose,
            frequence = medDTO.frequence,
            patient = patient
        )
        medicamentService.ajouterMedicament(medicament)
        return "le médicament '${medicament.nom}' a été ajouté au patient '${patient.nom}'."
    }

    @GetMapping("/all")
    fun afficherTousLesMedicaments(): List<Medicament> {
        return medicamentService.afficherTousLesMedicaments()
    }
    @GetMapping("/{id}")
    fun chercherMedicament(@PathVariable id: Long): Medicament? {
        return medicamentService.chercherMedicamentParId(id)
    }
    @PutMapping("/update")
    fun modifierMedicament(@Valid @RequestBody med: Medicament): Medicament {
        return medicamentService.modifierMedicament(med)
    }

    @DeleteMapping("/delete/{id}")
    fun supprimerMedicament(@PathVariable id: Long) {
        medicamentService.supprimerMedicament(id)
    }
}
