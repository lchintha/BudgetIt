package com.iquad.budgetit.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: BudgetEntity)

    @Update
    suspend fun updateBudget(budget: BudgetEntity)

    // Get the latest budget
    @Query("SELECT * FROM budget_table ORDER BY timestamp DESC LIMIT 1")
    fun getLatestBudget(): Flow<BudgetEntity?>

    // Get the budget history
    @Query("SELECT * FROM budget_table ORDER BY timestamp DESC")
    fun getBudgetHistory(): Flow<List<BudgetEntity>>

    @Query("SELECT COUNT(*) FROM categories_table")
    suspend fun getCategoryCount(): Int

    @Query("SELECT * FROM categories_table")
    fun getCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategories(categories: List<Category>)

    @Transaction
    suspend fun deleteCategory(oldCategoryId: Int, newCategoryId: Int) {
        // First, update all expenses with the current category to the new category
        updateExpenseCategoriesToNew(oldCategoryId, newCategoryId)
        // Then delete the category
        deleteCategoryById(oldCategoryId)
    }

    @Transaction
    suspend fun deleteCategoryIncludingExpenses(categoryId: Int) {
        //First, delete all expenses associated with the category
        deleteExpensesByCategory(categoryId)
        // Then delete the category
        deleteCategoryById(categoryId)
    }

    // Actual deletion of the category by ID
    @Query("DELETE FROM categories_table WHERE id = :categoryId")
    suspend fun deleteCategoryById(categoryId: Int)

    // Insert a single expense
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseWithCategoryId)

    // Update an existing expense
    @Update
    suspend fun updateExpense(expense: ExpenseWithCategoryId)

    // Delete expense by ID
    @Query("DELETE FROM expenses_table WHERE id = :expenseId")
    suspend fun deleteExpenseById(expenseId: Int)

    // Get all expenses
    @Transaction
    @Query("SELECT * FROM expenses_table ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    // Get expenses by month (assuming date is in 'YYYY-MM-DD' format)
    @Transaction
    @Query("SELECT * FROM expenses_table WHERE substr(date, 1, 7) = :month ORDER BY date DESC")
    fun getExpensesByMonth(month: String): Flow<List<Expense>>

    // Update expenses to the new category before deletion
    @Query("UPDATE expenses_table SET category_id = :newCategoryId WHERE category_id = :oldCategoryId")
    suspend fun updateExpenseCategoriesToNew(oldCategoryId: Int, newCategoryId: Int)

    // Check if a category has any expenses
    @Query("SELECT COUNT(*) FROM expenses_table WHERE category_id = :categoryId")
    suspend fun countExpensesInCategory(categoryId: Int): Int

    // Delete expenses by category
    @Query("DELETE FROM expenses_table WHERE category_id = :categoryId")
    suspend fun deleteExpensesByCategory(categoryId: Int)

    // Get expenses in a timeframe
    // Date format YYYY-MM-DD
    @Transaction
    @Query(
        """
    SELECT * 
    FROM expenses_table
    WHERE date(date) BETWEEN date(:startDate) AND date(:endDate)
    ORDER BY date DESC
"""
    )
    fun getExpensesByTimeFrame(startDate: String, endDate: String): Flow<List<Expense>>

}