package com.iquad.budgetit


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.iquad.budgetit.presentation.budget.BudgetSetupScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        BudgetSetupScreen()
    }
}