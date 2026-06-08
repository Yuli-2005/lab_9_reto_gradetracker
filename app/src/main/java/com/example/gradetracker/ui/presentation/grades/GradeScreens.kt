package com.example.gradetracker.ui.presentation.grades

import com.example.gradetracker.domain.model.AcademicGrade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeTrackerApp(viewModel: GradeViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()

    when (currentScreen) {
        Screen.GradeList -> GradeListScreen(
            uiState = uiState,
            onAddGradeClick = { viewModel.navigateToGradeForm() }
        )
        Screen.GradeForm -> GradeFormScreen(
            uiState = uiState,
            onSaveGrade = { activityName, subject, gradeInput ->
                viewModel.addGrade(activityName, subject, gradeInput)
            },
            onCancel = { viewModel.navigateToGradeList() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeListScreen(
    uiState: GradeUiState,
    onAddGradeClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddGradeClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar calificación")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is GradeUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is GradeUiState.Success -> {
                    GradeListContent(
                        grades = uiState.grades,
                        average = uiState.average
                    )
                }
                is GradeUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text(
                                text = "Error: ${uiState.message}",
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GradeListContent(grades: List<AcademicGrade>, average: Double) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Promedio General Acumulado",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = String.format("%.2f", average),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        items(grades) { grade ->
            GradeItem(grade = grade)
        }
    }
}

@Composable
fun GradeItem(grade: AcademicGrade) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = grade.activityName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = grade.subject,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = String.format("%.1f", grade.grade),
                style = MaterialTheme.typography.headlineSmall,
                color = when {
                    grade.grade >= 7.0 -> MaterialTheme.colorScheme.primary
                    grade.grade >= 5.0 -> MaterialTheme.colorScheme.secondary
                    else -> MaterialTheme.colorScheme.error
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeFormScreen(
    uiState: GradeUiState,
    onSaveGrade: (String, String, String) -> Unit,
    onCancel: () -> Unit
) {
    var activityName by rememberSaveable { mutableStateOf("") }
    var subject by rememberSaveable { mutableStateOf("") }
    var gradeInput by rememberSaveable { mutableStateOf("") }


    val isFormValid = activityName.isNotBlank() && subject.isNotBlank() && gradeInput.isNotBlank()
    val errorMessage = if (uiState is GradeUiState.Error) uiState.message else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Calificación") },
                navigationIcon = {
                    TextButton(onClick = onCancel) {
                        Text("Cancelar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = activityName,
                onValueChange = { activityName = it },
                label = { Text("Nombre de la actividad") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage != null && activityName.isBlank()
            )
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Asignatura") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage != null && subject.isBlank()
            )
            OutlinedTextField(
                value = gradeInput,
                onValueChange = { gradeInput = it },
                label = { Text("Nota (0.0 - 10.0)") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage != null && gradeInput.isBlank(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = { onSaveGrade(activityName, subject, gradeInput) },
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid
            ) {
                Text("Registrar")
            }
        }
    }
}