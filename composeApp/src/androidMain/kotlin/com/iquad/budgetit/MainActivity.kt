package com.iquad.budgetit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.iquad.budgetit.presentation.budget.BudgetSetupScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    MaterialTheme {
        BudgetSetupScreen()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}