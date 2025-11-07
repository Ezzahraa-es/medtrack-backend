# 🩺 MedTrack — Application de Suivi Médical à Domicile

## 🧠 Description
**MedTrack** est une application de suivi des traitements médicaux à domicile, développée avec **Spring Boot** et **Kotlin**.  
Elle permet à chaque médecin de gérer ses patients, leurs médicaments, ainsi que le suivi des prises quotidiennes.  
Le but principal est d’aider les médecins à assurer un suivi précis et continu des traitements, en visualisant les prises effectuées ou oubliées.

---

## ⚙️ Technologies utilisées
- Kotlin
- Spring Boot 3
- Spring Data JPA
- MySQL
- Postman
- IntelliJ IDEA
- Architecture : MVC (Model – View – Controller)
- Build Tool : Gradle

---

## 🧱 Architecture
Le projet suit une architecture en couches :
- **Entity** : Représentation des tables de la base de données
- **Repository** : Gestion CRUD
- **Service** : Logique métier
- **Controller** : Endpoints REST

---

## 🧩 Diagramme UML
Le diagramme suivant représente les relations entre les entités principales du projet :

![Diagramme UML](/uml_diagramm.jpg)

> 💡 Relations principales :
> - Un **Médecin** peut avoir plusieurs **Patients**
> - Un **Patient** peut avoir plusieurs **Médicaments**
> - Un **Médicament** est associé à plusieurs **Prises**

---

## 🗂️ Structure de la Base de Données

### 🧍‍♂️ Entité : Patient
- Contient les informations personnelles du patient : nom, prénom, âge, maladie.
- Chaque patient est suivi par un seul médecin et peut avoir plusieurs médicaments.

### 🧑‍⚕️ Entité : Médecin
- Contient les informations du médecin : nom, prénom, spécialité.
- Chaque médecin gère plusieurs patients et peut consulter leurs dossiers complets.

### 💊 Entité : Médicament
- Contient les informations sur le médicament : nom, dose, fréquence.
- Chaque médicament est attribué à un seul patient.
- Contient une liste de prises associées.

### ⏰ Entité : Prise
- Contient les informations sur les prises de médicaments : date, heure, état (effectuée ou oubliée).
- Chaque prise est liée à un médicament et un patient.

---

## 🚀 Installation et exécution
### Prérequis :
- JDK 17+
- MySQL
- Gradle
- Postman

---

### Étapes d'installation
1. Clonez le repository :
   ```bash
   git clone https://github.com/Ezzahraa-es/medtrack-backend.git


---

## 📡 Endpoints Disponibles

## 👩‍⚕️ Médecin

- GET /medecins/all → Afficher tous les médecins

- POST /medecins/add → Ajouter un nouveau médecin

- DELETE /medecins/delete/{id} → Supprimer un médecin

- GET /patients/{id}/dossier → Consulter le dossier complet d’un patient

## 🧍 Patient

- GET /patients/all → Afficher tous les patients
- GET /patients/paged-text?page=0&size=5&sortBy=nom → Afficher tous les patients sous forme des pages ou chaque page conte 5 patients sorted selon leurs noms

- POST /patients/add → Ajouter un patient

- PUT /patients/update → Modifier un patient

- DELETE /patients/delete/{id} → Supprimer un patient

## 💊 Médicament

- POST /medicaments/add → Ajouter un médicament à un patient

- GET /medicaments/all → Voir tous les médicaments

- DELETE /medicaments/delete/{id} → Supprimer un médicament

## ⏰ Prise

- POST /prises/add → Enregistrer une prise (effectuée ou oubliée)

- GET /prises/oubliees/{patientId}/{medicamentId} → Voir les prises oubliées d’un patient pour un médicament donné

---

## Améliorations Apportées
Dans ce projet MedTrack, j'ai implémenté plusieurs améliorations pour renforcer la sécurité et la maintenabilité de l'application backend, en suivant les bonnes pratiques de Spring Boot et Kotlin.

- **Validations avec Annotations dans les Entités** : J'ai ajouté des validations directement dans les entités (comme `Patient`) pour vérifier les données au niveau de la couche métier. Par exemple, dans l'entité `Patient` :
    - `@NotBlank` pour s'assurer que les champs comme `nom`, `prenom` et `maladie` ne sont pas vides ou null.
    - `@Min` et `@Max` pour contraindre l'âge entre 1 et 120 ans, évitant les valeurs absurdes.
      Ces validations sont appliquées automatiquement lors des opérations CRUD (Create, Read, Update, Delete) via l'API, réduisant les erreurs et améliorant la fiabilité. Elles ont été choisies pour une validation côté serveur simple et efficace, sans dépendre uniquement du front-end.

- **Utilisation de DTOs (Data Transfer Objects)** : Pour séparer les données de l'API des entités de base de données, j'ai créé des DTOs légers pour `Medicament` et `Prise`. Exemples :
    - `MedicamentDTO` : Contient seulement les champs essentiels (`nom`, `dose`, `frequence`, `patientId`) pour éviter l'exposition de données internes et optimiser les transferts.
    - `PriseDTO` : Inclut `heure`, `date` et `medicamentId`, permettant une gestion précise des prises de médicaments sans surcharger l'API.
    - `PatientDTO` : Inclut  nom , prenom , age, maladie et medecinId permettant une gestion precis des patients selon un medecin precis sans surcharger l'API.
      Ces DTOs facilitent la validation et la transformation des données, tout en protégeant contre les fuites d'informations sensibles.

- **Gestion Centralisée des Exceptions** : J’ai ajouté un Global Exception Handler avec:
    - Une exception personnalisée : `ResourceNotFoundException`
    - Une classe `@RestControllerAdvice` pour gérer toutes les erreurs
      Le système renvoie automatiquement des réponses claires et structurées.
  
- **Pagination & Tri des Patients** : J’ai ajouté un endpoint permettant:
    - La pagination (page, size)
    - Le tri (sortBy, ex. : nom, prenom, age)
    - Exemple : `GET /patients/paged-text?page=0&size=5&sortBy=nom`
      Cette pagination rend le chargement rapide même avec beaucoup de données

Ces améliorations ont été choisies pour :
- **Sécurité** : Les validations et DTOs empêchent les données invalides ou malicieuses, protégeant l'application contre les attaques courantes (ex. : injection de données).
- **Maintenabilité** : gestion d’erreurs centralisée, code propre et facile à maintenir
- **Performance** : pagination, DTOs plus légers.
- **Conformité aux bonnes pratiques** : architecture claire, API robuste et évolutive
---

## 👩‍💻 Auteur
*Ezzahraa Essadiki* et *Ahlam Sour*   — Projet Back-End Kotlin / Spring Boot


---

## 📅 Date
Novembre 2025