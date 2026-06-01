package com.example.ecotrip2026g4.ui.presentation.grades

sealed class Screen(val route: String) {
    object GradeList : Screen("grade_list")
    object GradeForm : Screen("grade_form")
}