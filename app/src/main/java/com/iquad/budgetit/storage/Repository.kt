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

    // Delete a single expense
    suspend fun deleteExpense(expense: ExpenseWithCategoryId) {
        appDao.deleteExpense(expense)
    }

    // Get expenses by month
    fun getExpensesByMonth(month: String): Flow<List<Expense>> {
        return appDao.getExpensesByMonth(month)
    }

    // Get total expenses for a specific month
    fun getTotalExpensesByMonth(month: String): Flow<Double> {
        return appDao.getTotalExpensesByMonth(month)
    }

    // Check if a category has any expenses
    suspend fun countExpensesInCategory(categoryId: Int): Int {
        return appDao.countExpensesInCategory(categoryId)
    }

}