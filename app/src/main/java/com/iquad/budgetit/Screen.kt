package com.iquad.budgetit

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

sealed class Screen(val route: String){
    data object EnterBudget : Screen("enter_budget")
    data object HomeScreen : Screen("home_screen")
    data object AddExpenseScreen : Screen("add_expense_screen?expenseId={expenseId}") {
        fun createRoute(expenseId: Int? = null): String {
            return if (expenseId != null) {
                "add_expense_screen?expenseId=$expenseId"
            } else {
                "add_expense_screen?expenseId=-1"
            }
        }
    }
    data object SettingsScreen : Screen("settings_screen")
    data object AllExpensesScreen : Screen("all_expenses_screen?categoryId={categoryId}") {
        fun createRoute(categoryId: Int? = null): String {
            return if (categoryId != null) {
                "all_expenses_screen?categoryId=$categoryId"
            } else {
                "all_expenses_screen?categoryId=-1"
            }
        }
    }
    data object AddCategory : Screen("add_category")
    data object SpendingAnalysisScreen : Screen("spending_analysis_screen")
    data object WebPage : Screen("webpage?url={url}&title={title}") {
        fun createRoute(url: String, title: String): String {
            return try {
                val encodedUrl = URLEncoder.encode(url, "UTF-8")
                val encodedTitle = URLEncoder.encode(title, "UTF-8")
                "webpage?url=$encodedUrl&title=$encodedTitle"
            } catch (e: UnsupportedEncodingException) {
                "webpage?url=&title="
            }
        }
    }
}