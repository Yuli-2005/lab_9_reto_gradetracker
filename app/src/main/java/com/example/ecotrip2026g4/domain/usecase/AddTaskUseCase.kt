package com.example.ecotrip2026g4.domain.usecase

import com.example.ecotrip2026g4.domain.repository.AcademicTaskRepository

class AddTaskUseCase(private val repository: AcademicTaskRepository) {
    suspend operator fun invoke(title: String) {
        if (title.trim().length < 5) {
            throw IllegalArgumentException("La regla de dominio exige un mínimo de 5 caracteres.")
        }
        repository.addTask(title.trim())
    }
}