package com.example.ecotrip2026g4.domain.model

import java.util.UUID

/**
 * Modelo de dominio inmutable que representa una calificación académica.
 * @param id Identificador único (por defecto se genera automáticamente)
 * @param activityName Nombre de la actividad evaluada
 * @param subject Asignatura a la que pertenece
 * @param grade Nota numérica en el rango [0.0, 10.0]
 */
data class AcademicGrade(
    val id: String = UUID.randomUUID().toString(),
    val activityName: String,
    val subject: String,
    val grade: Double
)