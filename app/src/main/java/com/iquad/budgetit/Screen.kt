package com.iquad.budgetit

sealed class Screen(val route: String){
    data object EnterBudget : Screen("enter_budget")
    data object HomeScreen : Screen("home_screen")
    data object AddExpenseScreen : Screen("add_expense_screen")
}