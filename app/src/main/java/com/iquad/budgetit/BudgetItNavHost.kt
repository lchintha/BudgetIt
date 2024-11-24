package com.iquad.budgetit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iquad.budgetit.enterbudget.EnterBudgetScreen

@Composable
fun BudgetItNavHost(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.EnterBudget.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.EnterBudget.route) {
            EnterBudgetScreen(navController)
        }
    }
}