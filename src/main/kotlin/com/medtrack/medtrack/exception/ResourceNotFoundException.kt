package com.medtrack.medtrack.exception

/**
 * exception personnalisée lancée lorsqu'une ressource demandée
 * (patient, médecin, médicament, etc.) n'existe pas dans la base de données.
 *
 * cette exception permet une gestion plus propre des erreurs
 * en séparant les cas d'absence de données des autres exceptions.
 *
 * @param message message explicatif affiché dans la réponse d'erreur
 */
class ResourceNotFoundException(message: String) : RuntimeException(message)