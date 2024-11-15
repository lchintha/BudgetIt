package com.iquad.budgetit.presentation.budget

data class BudgetSetupState(
    val amount: String = "0",
    val selectedCurrency: String = "$",
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isBudgetSet: Boolean = false
)