package com.medtrack.medtrack.service

import com.medtrack.medtrack.entity.Patient
import com.medtrack.medtrack.repository.MedicamentRepository
import com.medtrack.medtrack.repository.PatientRepository
import com.medtrack.medtrack.repository.PriseRepository
import org.springframework.stereotype.Service
import org.springframework.data.domain.*
import com.medtrack.medtrack.exception.ResourceNotFoundException
import com.medtrack.medtrack.entity.PatientDTO
import com.medtrack.medtrack.repository.MedecinRepository

/**
 * Service pour la gestion des patients dans le système MedTrack.
 *
 * cette classe contient les opérations métier liées aux patients, telles que
 * l'ajout, la modification, la suppression, la recherche et la consultation
 * du dossier complet d'un patient. elle communique avec les repositories
 * [PatientRepository], [MedicamentRepository] et [PriseRepository] pour
 * interagir avec la base de données.
 *
 * Fonctions principales :
 * - ajouterPatient : ajouter un nouveau patient
 * - afficherTousLesPatients : récupérer la liste de tous les patients
 * - chercherPatientParId : rechercher un patient par son identifiant
 * - modifierPatient : modifier les informations d'un patient existant
 * - supprimerPatient : supprimer un patient par son identifiant
 * - consulterDossierComplet : consulter le dossier complet d'un patient
 */

@Service
class PatientService( private val patientRepository: PatientRepository ,
                            private val medicamentRepository: MedicamentRepository ,
                            private val priseRepository: PriseRepository ,
                            private val medecinRepository: MedecinRepository) {

    /**
     * récupère une page de patients depuis la base de données, avec possibilité
     * de trier les résultats puis de les retourner sous forme de texte lisible.
     *
     * cette méthode est utilisée pour éviter les problèmes de récursion
     * lors de la sérialisation JSON (par exemple relations Patient → Médicaments → Patient).
     *
     * @param page numéro de la page à afficher (0 = première page)
     * @param size nombre d'éléments par page
     * @param sortBy champ utilisé pour le tri (ex : "nom", "age")
     * @return texte contenant les informations des patients de la page demandée
     */
    fun afficherPagePatients(page: Int, size: Int, sortBy: String): String {
        val pageable = PageRequest.of(page, size, Sort.by(sortBy))
        val pageResult = patientRepository.findAll(pageable)

        if (pageResult.isEmpty) {
            return "Aucun patient à afficher dans cette page."
        }

        var resultat = "Page ${pageResult.number + 1}/${pageResult.totalPages}\n"
        resultat += "Nombre total de patients : ${pageResult.totalElements}\n"
        resultat += "Taille de page : ${pageResult.size}\n\n"

        for (patient in pageResult.content) {
            resultat += "Patient : ${patient.nom} ${patient.prenom}\n"
            resultat += "Âge : ${patient.age} \nMaladie : ${patient.maladie}\n"

            if (patient.medicaments.isNotEmpty()) {
                resultat += "Médicaments :\n"
                for (m in patient.medicaments) {
                    resultat += "- ${m.nom} (${m.dose}, ${m.frequence})\n"
                }
            } else {
                resultat += "Aucun médicament enregistré.\n"
            }

            resultat += "\n-----------------------------\n\n"
        }

        return resultat
    }


    /**
     * Ajoute un nouveau patient en utilisant les données reçues dans un DTO.
     *
     * Cette méthode :
     * - vérifie que le médecin existe,
     * - crée un nouvel objet Patient avec les informations fournies,
     * - enregistre le patient dans la base de données,
     * - renvoie un message texte indiquant le succès de l'opération.
     *
     * @param dto objet contenant les données nécessaires pour créer le patient
     * @return message textuel confirmant l'ajout du patient ou indiquant une erreur
     * @throws ResourceNotFoundException si le médecin fourni n'existe pas
     */

    fun ajouterPatient(dto: PatientDTO): String {
        val medecin = medecinRepository.findById(dto.medecinId)
            .orElseThrow { ResourceNotFoundException("Médecin introuvable") }

        val patient = Patient(
            nom = dto.nom,
            prenom = dto.prenom,
            age = dto.age,
            maladie = dto.maladie,
            medecin = medecin
        )
        patientRepository.save(patient)
        return "Patient ${dto.nom} ${dto.prenom} ajouté avec succès au médecin ${medecin.nom} ${medecin.prenom}."
//        return patientRepository.save(patient)  (pour un objet json)
    }

    /**
     * récupère la liste de tous les patients.
     *
     * @return liste de tous les patients enregistrés
     */

    fun afficherTousLesPatients(): List<Patient> {
        return patientRepository.findAll()
    }
    /**
     * recherche un patient par son identifiant.
     *
     * @param id identifiant du patient
     * @return le patient correspondant
     * @throws ResourceNotFoundException si le patient n'existe pas
     */

    fun chercherPatientParId(id: Long): Patient {
        return patientRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Patient avec ID $id introuvable") }
    }
    /**
     * modifie un patient existant dans la base de données.
     *
     * @param patient objet patient contenant les nouvelles informations
     * @return le patient modifié
     */

    fun modifierPatient(patient: Patient): Patient {
        return patientRepository.save(patient)
    }
    /**
     * supprime un patient par son identifiant.
     *
     * @param id identifiant du patient à supprimer
     */

    fun supprimerPatient(id: Long) {
        patientRepository.deleteById(id)
    }
    /**
     * Consulte le dossier complet d’un patient, incluant :
     * - ses informations personnelles
     * - les médicaments qui lui sont associés
     * - les prises effectuées ou oubliées
     *
     * Si le patient n’existe pas, une ResourceNotFoundException est levée.
     *
     * @param patientId identifiant du patient
     * @return chaîne textuelle décrivant le dossier complet du patient
     * @throws ResourceNotFoundException si aucun patient n’est trouvé
     */
    fun consulterDossierComplet(patientId: Long): String {

        val patient = patientRepository.findById(patientId)
            .orElseThrow {  ResourceNotFoundException("Aucun patient trouvé avec l'ID $patientId")}

        val medicamentsPatient = medicamentRepository.findAll().filter { it.patient?.id == patientId }

        val prisesPatient = priseRepository.findAll().filter { it.patient?.id == patientId }

        var prisesEffectuees = 0
        var prisesOubliees = 0

        for (prise in prisesPatient) {
            if (prise.etat == true) {
                prisesEffectuees++
            } else if (prise.etat == false) {
                prisesOubliees++
            }
        }

        val resultat = """
        Dossier du patient :
        Nom : ${patient.nom}
        Prénom : ${patient.prenom}
        Maladie : ${patient.maladie}
        Âge : ${patient.age}

        Médicaments :
        ${medicamentsPatient.joinToString("\n") { "- ${it.nom} (${it.dose}, ${it.frequence})" }}

        Prises enregistrées : ${prisesPatient.size}
         Effectuées : $prisesEffectuees
         Oubliées : $prisesOubliees
    """.trimIndent()

        return resultat
    }

}