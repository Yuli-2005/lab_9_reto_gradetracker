package com.example.gradetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gradetracker.data.repository.InMemoryGradeRepository
import com.example.gradetracker.domain.usecase.AddGradeUseCase
import com.example.gradetracker.domain.usecase.GetGradesUseCase
import com.example.gradetracker.ui.presentation.grades.GradeTrackerApp
import com.example.gradetracker.ui.presentation.grades.GradeViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: GradeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = InMemoryGradeRepository()
                val getGradesUseCase = GetGradesUseCase(repository)
                val addGradeUseCase = AddGradeUseCase(repository)
                return GradeViewModel(getGradesUseCase, addGradeUseCase) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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