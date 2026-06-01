package com.example.ecotrip2026g4.data.repository

import com.example.ecotrip2026g4.domain.model.AcademicGrade
import com.example.ecotrip2026g4.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Repositorio simulado en memoria que implementa [GradeRepository].
 * Utiliza [MutableStateFlow] para mantener el estado reactivo de la lista de calificaciones.
 */
class InMemoryGradeRepository : GradeRepository {

    // Fuente mutable privada
    private val _gradesFlow = MutableStateFlow<List<AcademicGrade>>(emptyList())

    // Flujo público de solo lectura para la capa de dominio
    override fun getAllGrades(): Flow<List<AcademicGrade>> = _gradesFlow.asStateFlow()

    init {
        // Precargar dos registros académicos de muestra
        loadSampleData()
    }

    private fun loadSampleData() {
        val sampleGrades = listOf(
            AcademicGrade(
                activityName = "Examen Parcial",
                subject = "Matemáticas",
                grade = 8.5
            ),
            AcademicGrade(
                activityName = "Trabajo Final",
                subject = "Programación",
                grade = 9.0
            )
        )
        _gradesFlow.update { sampleGrades }
    }

    override suspend fun addGrade(grade: AcademicGrade): Boolean {
        return try {
            _gradesFlow.update { currentList ->
                currentList + grade
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}