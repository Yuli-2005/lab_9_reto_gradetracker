package com.example.ecotrip2026g4.domain.repository

import com.example.ecotrip2026g4.domain.model.AcademicGrade
import kotlinx.coroutines.flow.Flow

/**
 * Contrato del repositorio para operaciones de acceso a datos de calificaciones.
 * Las implementaciones concretas (ej. en memoria, con Room, etc.) deben cumplir esta interfaz.
 */
interface GradeRepository {

    /**
     * Obtiene un flujo reactivo de la lista de calificaciones.
     * Cada emisión refleja el estado más actualizado de la fuente de datos.
     */
    fun getAllGrades(): Flow<List<AcademicGrade>>

    /**
     * Agrega una nueva calificación.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    suspend fun addGrade(grade: AcademicGrade): Boolean
}