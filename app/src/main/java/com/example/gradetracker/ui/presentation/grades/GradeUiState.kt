package com.example.gradetracker.ui.presentation.grades

import com.example.gradetracker.domain.model.AcademicGrade

sealed class GradeUiState {
    object Loading : GradeUiState()
    data class Success(val grades: List<AcademicGrade>, val average: Double) : GradeUiState()
    data class Error(val message: String) : GradeUiState()
}