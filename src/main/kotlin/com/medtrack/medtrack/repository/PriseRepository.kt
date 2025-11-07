package com.medtrack.medtrack.repository


import com.medtrack.medtrack.entity.Prise
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Repository pour la gestion des prises de médicaments dans le système MedTrack.
 *
 * cette interface hérite de [JpaRepository] et fournit les opérations de
 * base pour accéder à la table des prises dans la base de données, telles que :
 * - création
 * - lecture
 * - mise à jour
 * - suppression
 *
 * JpaRepository fournit déjà toutes ces méthodes, il n'est donc pas nécessaire
 * d'implémenter manuellement ces opérations.
 *
 * @see Prise
 */

interface PriseRepository : JpaRepository<Prise, Long>