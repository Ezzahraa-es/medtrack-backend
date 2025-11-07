package com.medtrack.medtrack.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * gestion centralisée des exceptions dans l'application.
 *
 * cette classe intercepte automatiquement toutes les exceptions
 * lancées dans les contrôleurs et renvoie des réponses structurées
 * avec des codes HTTP appropriés.
 *
 * Gère notamment :
 * - resourceNotFoundException -> 404
 * - validation errors (@Valid) -> 400
 * - exceptions générales -> 500
 */
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ResponseEntity<Map<String, String>> {
        val body = mapOf(
            "error" to ex.message!!,
            "status" to "404 NOT FOUND"
        )
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<Map<String, String>> {
        val body = mapOf(
            "error" to "Erreur interne du serveur",
            "details" to ex.message.toString()
        )
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
