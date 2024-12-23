package com.iquad.budgetit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iquad.budgetit.enterbudget.EnterBudgetScreen
import com.iquad.budgetit.expenses.AddCategory
import com.iquad.budgetit.expenses.AddExpenseScreen
import com.iquad.budgetit.expenses.AllExpensesScreen
import com.iquad.budgetit.expenses.SpendingAnalysisScreen
import com.iquad.budgetit.homescreen.HomeScreen
import com.iquad.budgetit.settings.SettingsScreen
import com.iquad.budgetit.viewmodel.BudgetItViewModel

@Composable
fun BudgetItNavHost(
    innerPadding: PaddingValues,
    startDestination: String = Screen.EnterBudget.route,
    viewModel: BudgetItViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.EnterBudget.route) {
            EnterBudgetScreen(
                navController,
                viewModel
            )
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                navController,
                viewModel
            )
        }
        composable(
            route = Screen.AddExpenseScreen.route,
            arguments = listOf(navArgument("expenseId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getInt("expenseId")?.let { id ->
                if (id == -1) null else id
            }
            AddExpenseScreen(
                navController = navController,
                viewModel = viewModel,
                expenseId = expenseId
            )
        }
        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen(
                navController,
                viewModel
            )
        }
        composable(
            route = Screen.AllExpensesScreen.route,
            arguments = listOf(navArgument("categoryId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) {
            val categoryId = it.arguments?.getInt("categoryId")?.let { id ->
                if (id == -1) null else id
            }
            AllExpensesScreen(
                navController,
                viewModel,
                categoryId
            )
        }
        composable(route = Screen.AddCategory.route) {
            AddCategory(
                navController,
                viewModel
            )
        }
        composable(route = Screen.SpendingAnalysisScreen.route) {
            SpendingAnalysisScreen(
                navController,
                viewModel
            )
        }
    }
}