package com.example.ecotrip2026g4.domain.usecase

import com.example.ecotrip2026g4.domain.model.AcademicTask
import com.example.ecotrip2026g4.domain.repository.AcademicTaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(private val repository: AcademicTaskRepository) {
    operator fun invoke(): Flow<List<AcademicTask>> = repository.getTasksStream()
}