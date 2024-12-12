package com.iquad.budgetit.storage

import kotlinx.coroutines.flow.Flow

class BudgetItRepository(private val appDao: AppDao) {

    val budget: Flow<List<BudgetEntity>> = appDao.getBudget()

    suspend fun insertBudget(budget: BudgetEntity) {
        appDao.insertBudget(budget)
    }

    suspend fun updateBudget(budget: BudgetEntity) {
        appDao.updateBudget(budget)
    }

}