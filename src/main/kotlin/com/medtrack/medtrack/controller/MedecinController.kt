package com.medtrack.medtrack.controller

import com.medtrack.medtrack.entity.Medecin
import com.medtrack.medtrack.entity.Patient
import com.medtrack.medtrack.service.MedecinService
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid


@RestController
@RequestMapping("/medecins")
class MedecinController(private val medecinService: MedecinService) {

    @PostMapping("/add")
    fun ajouterMedecin(@Valid@RequestBody medecin: Medecin): Medecin {
        return medecinService.ajouterMedecin(medecin)
    }
    @GetMapping("/all")
    fun afficherTousLesMedecins(): List<Medecin> {
        return medecinService.afficherTousLesMedecins()
    }
    @GetMapping("/{id}")
    fun chercherMedecin(@PathVariable id: Long): Medecin? {
        return medecinService.chercherMedecinParId(id)
    }
    @PutMapping("/update")
    fun modifierMedecin(@Valid@RequestBody medecin: Medecin): Medecin {
        return medecinService.modifierMedecin(medecin)
    }

    @DeleteMapping("/delete/{id}")
    fun supprimerMedecin(@PathVariable id: Long) {
        medecinService.supprimerMedecin(id)
    }

}
