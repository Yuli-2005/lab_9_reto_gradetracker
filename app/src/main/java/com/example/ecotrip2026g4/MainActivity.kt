package com.example.ecotrip2026g4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ecotrip2026g4.data.repository.InMemoryTaskRepository
import com.example.ecotrip2026g4.domain.usecase.AddTaskUseCase
import com.example.ecotrip2026g4.domain.usecase.GetTasksUseCase
import com.example.ecotrip2026g4.ui.presentation.tasks.AcademicTaskApp
import com.example.ecotrip2026g4.ui.presentation.tasks.AcademicTaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Instanciar dependencias
        val repository = InMemoryTaskRepository()
        val getTasksUseCase = GetTasksUseCase(repository)
        val addTaskUseCase = AddTaskUseCase(repository)

        // 2. Crear ViewModel
        val viewModel = AcademicTaskViewModel(getTasksUseCase, addTaskUseCase, repository)

        // 3. Establecer la UI
        setContent {
            AcademicTaskApp(viewModel = viewModel)
        }
    }
}