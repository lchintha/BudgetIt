package com.iquad.budgetit.storage

import kotlinx.coroutines.flow.Flow

class BudgetItRepository(private val appDao: AppDao) {

    val budget: Flow<BudgetEntity?> = appDao.getLatestBudget()

    suspend fun insertBudget(budget: BudgetEntity) {
        appDao.insertBudget(budget)
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

    suspend fun deleteCategory(oldCategoryId: Int, newCategoryId: Int) {
        appDao.deleteCategory(oldCategoryId, newCategoryId)
    }

    suspend fun deleteCategoryById(categoryId: Int) {
        appDao.deleteCategoryById(categoryId)
    }

    suspend fun deleteCategoryIncludingExpenses(categoryId: Int) {
        appDao.deleteCategoryIncludingExpenses(categoryId)
    }

    // Insert a single expense
    suspend fun insertExpense(expense: ExpenseWithCategoryId) {
        appDao.insertExpense(expense)
    }

    // Update an existing expense
    suspend fun updateExpense(expense: ExpenseWithCategoryId) {
        appDao.updateExpense(expense)
    }

    // Delete expense by ID
    suspend fun deleteExpenseById(expenseId: Int) {
        appDao.deleteExpenseById(expenseId)
    }

    // Get expenses by month
    fun getExpensesByMonth(month: String): Flow<List<Expense>> {
        return appDao.getExpensesByMonth(month)
    }

    // Check if a category has any expenses
    suspend fun countExpensesInCategory(categoryId: Int): Int {
        return appDao.countExpensesInCategory(categoryId)
    }

    // Get expenses in a timeframe
    fun getExpensesByTimeFrame(startDate: String, endDate: String): Flow<List<Expense>> {
        return appDao.getExpensesByTimeFrame(startDate, endDate)
    }

}