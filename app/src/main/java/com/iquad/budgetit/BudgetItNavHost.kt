package com.iquad.budgetit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iquad.budgetit.enterbudget.EnterBudgetScreen
import com.iquad.budgetit.expenses.AddExpenseScreen
import com.iquad.budgetit.expenses.AllExpensesScreen
import com.iquad.budgetit.homescreen.HomeScreen
import com.iquad.budgetit.settings.SettingsScreen

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
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.AddExpenseScreen.route) {
            AddExpenseScreen(navController)
        }
        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen(navController)
        }
        composable(route = Screen.AllExpensesScreen.route) {
            AllExpensesScreen(navController)
        }
    }
}