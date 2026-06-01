package com.example.ecotrip2026g4.ui.presentation.tasks

import com.example.ecotrip2026g4.domain.model.AcademicTask

sealed class AcademicTaskUiState {
    object Loading : AcademicTaskUiState()
    data class Success(val tasks: List<AcademicTask>) : AcademicTaskUiState()
    data class Error(val message: String) : AcademicTaskUiState()
}