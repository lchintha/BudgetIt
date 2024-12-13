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

    val categories: Flow<List<Category>> = appDao.getCategories()

    suspend fun insertCategory(category: Category) {
        appDao.insertCategory(category)
    }

    suspend fun getCategoryCount(): Int {
        return appDao.getCategoryCount()
    }

    suspend fun insertCategories(categories: List<Category>) {
        appDao.insertCategories(categories)
    }

    suspend fun deleteCategory(category: Category) {
        appDao.deleteCategory(category)
    }

}