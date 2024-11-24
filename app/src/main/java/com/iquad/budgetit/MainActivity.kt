package com.iquad.budgetit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.iquad.budgetit.ui.theme.BudgetItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetItTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BudgetItNavHost(innerPadding)
                }
            }
        }
    }
}