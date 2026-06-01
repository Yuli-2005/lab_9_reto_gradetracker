package com.example.ecotrip2026g4.domain.usecase

import com.example.ecotrip2026g4.domain.model.AcademicGrade
import com.example.ecotrip2026g4.domain.repository.GradeRepository

class AddGradeUseCase(
    private val repository: GradeRepository
) {
    /**
     * Ejecuta la validación de dominio y delega la persistencia.
     * @return true si la nota es válida y se guardó, false si falló la validación o el repositorio.
     */
    suspend operator fun invoke(grade: AcademicGrade): Boolean {
        // Validación de dominio: nota entre 0.0 y 10.0
        if (grade.grade < 0.0 || grade.grade > 10.0) {
            return false
        }
        // Validación de campos no vacíos
        if (grade.activityName.isBlank() || grade.subject.isBlank()) {
            return false
        }
        return repository.addGrade(grade)
    }
}