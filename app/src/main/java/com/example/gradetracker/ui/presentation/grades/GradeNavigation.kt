package com.example.gradetracker.ui.presentation.grades

sealed class Screen(val route: String) {
    object GradeList : Screen("grade_list")
    object GradeForm : Screen("grade_form")
}