package com.example.ecotrip2026g4.domain.usecase

import com.example.ecotrip2026g4.domain.model.AcademicGrade
import com.example.ecotrip2026g4.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow

class GetGradesUseCase(
    private val repository: GradeRepository
) {
    operator fun invoke(): Flow<List<AcademicGrade>> = repository.getAllGrades()
}