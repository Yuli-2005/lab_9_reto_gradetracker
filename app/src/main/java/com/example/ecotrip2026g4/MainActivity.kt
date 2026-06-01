package com.example.ecotrip2026g4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.ecotrip2026g4.data.repository.InMemoryGradeRepository
import com.example.ecotrip2026g4.domain.usecase.AddGradeUseCase
import com.example.ecotrip2026g4.domain.usecase.GetGradesUseCase
import com.example.ecotrip2026g4.ui.presentation.grades.GradeTrackerApp
import com.example.ecotrip2026g4.ui.presentation.grades.GradeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = InMemoryGradeRepository()
        val getGradesUseCase = GetGradesUseCase(repository)
        val addGradeUseCase = AddGradeUseCase(repository)
        val viewModel = GradeViewModel(getGradesUseCase, addGradeUseCase)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GradeTrackerApp(viewModel = viewModel)
                }
            }
        }
    }
}