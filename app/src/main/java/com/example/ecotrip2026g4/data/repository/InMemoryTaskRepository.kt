package com.example.ecotrip2026g4.data.repository

import com.example.ecotrip2026g4.domain.model.AcademicTask
import com.example.ecotrip2026g4.domain.repository.AcademicTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

class InMemoryTaskRepository : AcademicTaskRepository {

    // Simulación de base de datos en memoria
    private val tasksFlow = MutableStateFlow<List<AcademicTask>>(
        listOf(
            AcademicTask(UUID.randomUUID().toString(), "Estudiar Patrón MVVM", false),
            AcademicTask(UUID.randomUUID().toString(), "Analizar principios de Clean Architecture", true)
        )
    )

    override fun getTasksStream(): Flow<List<AcademicTask>> = tasksFlow

    override suspend fun addTask(title: String) {
        val newTask = AcademicTask(
            id = UUID.randomUUID().toString(),
            title = title,
            isCompleted = false
        )
        tasksFlow.value = tasksFlow.value + newTask
    }

    override suspend fun toggleTaskCompletion(taskId: String) {
        tasksFlow.value = tasksFlow.value.map { task ->
            if (task.id == taskId) {
                task.copy(isCompleted = !task.isCompleted)
            } else {
                task
            }
        }
    }
}