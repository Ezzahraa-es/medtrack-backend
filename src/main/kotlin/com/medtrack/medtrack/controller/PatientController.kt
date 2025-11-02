package com.medtrack.medtrack.controller

import com.medtrack.medtrack.entity.Patient
import com.medtrack.medtrack.service.PatientService
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid


@RestController
@RequestMapping("/patients")
class PatientController(private val patientService: PatientService) {

    @PostMapping("/add")
    fun ajouterPatient(@Valid@RequestBody patient: Patient): Patient {
        return patientService.ajouterPatient(patient)
    }
    @GetMapping("/all")
    fun afficherTousLesPatients(): List<Patient> {
        return patientService.afficherTousLesPatients()
    }
    @GetMapping("/{id}")
    fun chercherPatient(@PathVariable id: Long): Patient? {
        return patientService.chercherPatientParId(id)
    }
    @PutMapping("/update")
    fun modifierPatient(@Valid@RequestBody patient: Patient): Patient {
        return patientService.modifierPatient(patient)
    }

    @DeleteMapping("/delete/{id}")
    fun supprimerPatient(@PathVariable id: Long) {
        patientService.supprimerPatient(id)
    }

    @GetMapping("/{id}/dossier")
    fun getDossierComplet(@PathVariable id: Long): String {
        return patientService.consulterDossierComplet(id)
    }
}
