package com.example.gradetracker.domain.usecase

import com.example.gradetracker.domain.model.AcademicGrade
import com.example.gradetracker.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow

class GetGradesUseCase(
    private val repository: GradeRepository
) {
    operator fun invoke(): Flow<List<AcademicGrade>> = repository.getAllGrades()
}