package com.iquad.budgetit.domain.repository

import com.iquad.budgetit.domain.model.Budget

interface BudgetRepository {
    suspend fun saveBudget(budget: Budget): Result<Unit>
    suspend fun getBudget(): Result<Budget?>
    suspend fun hasBudgetSet(): Result<Boolean>
}