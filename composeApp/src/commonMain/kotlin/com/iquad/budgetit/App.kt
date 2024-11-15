package com.iquad.budgetit


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.iquad.budgetit.presentation.budget.BudgetSetupScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed class Screen {
    data object BudgetSetup : Screen()
    data object Home : Screen() // Next screen after budget setup
}

@Composable
@Preview
fun App() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.BudgetSetup) }

    MaterialTheme {
        when (currentScreen) {
            Screen.BudgetSetup -> BudgetSetupScreen(
                onNavigateNext = { currentScreen = Screen.Home }
            )

            Screen.Home -> {
                // Home screen implementation
            }
        }
    }
}