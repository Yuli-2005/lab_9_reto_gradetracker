package com.example.gradetracker.ui.presentation.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradetracker.domain.model.AcademicGrade
import com.example.gradetracker.domain.usecase.AddGradeUseCase
import com.example.gradetracker.domain.usecase.GetGradesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GradeViewModel(
    private val getGradesUseCase: GetGradesUseCase,
    private val addGradeUseCase: AddGradeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<GradeUiState>(GradeUiState.Loading)
    val uiState: StateFlow<GradeUiState> = _uiState.asStateFlow()

    private val _currentScreen = MutableStateFlow<Screen>(Screen.GradeList)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    init {
        loadGrades()
    }

    private fun loadGrades() {
        viewModelScope.launch {
            getGradesUseCase()
                .map { grades ->
                    val average = if (grades.isNotEmpty()) grades.map { it.grade }.average() else 0.0
                    GradeUiState.Success(grades, average)
                }
                .catch { e ->
                    _uiState.value = GradeUiState.Error(e.message ?: "Error desconocido")
                }
                .collect { newState ->
                    _uiState.value = newState
                }
        }
    }

    fun addGrade(activityName: String, subject: String, gradeInput: String) {
        if (activityName.isBlank() || subject.isBlank() || gradeInput.isBlank()) {
            _uiState.value = GradeUiState.Error("Todos los campos son obligatorios")
            return
        }

        val grade = gradeInput.toDoubleOrNull()
        if (grade == null) {
            _uiState.value = GradeUiState.Error("La nota debe ser un número válido (ej: 7.5)")
            return
        }

        if (grade < 0.0 || grade > 10.0) {
            _uiState.value = GradeUiState.Error("La nota debe estar entre 0.0 y 10.0")
            return
        }

        val newGrade = AcademicGrade(
            activityName = activityName.trim(),
            subject = subject.trim(),
            grade = grade
        )

        viewModelScope.launch {
            val success = addGradeUseCase(newGrade)
            if (success) {
                _uiState.value = GradeUiState.Loading
                loadGrades()
                navigateToGradeList()
            } else {
                _uiState.value = GradeUiState.Error("Error al guardar la calificación. Verifique los datos.")
            }
        }
    }

    fun navigateToGradeForm() {
        _currentScreen.value = Screen.GradeForm
        if (_uiState.value is GradeUiState.Error) {
            _uiState.value = GradeUiState.Loading
            loadGrades()
        }
    }

    fun navigateToGradeList() {
        _currentScreen.value = Screen.GradeList
    }
}