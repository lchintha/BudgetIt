package com.iquad.budgetit.storage

import androidx.room.Dao
import androidx.room.Delete
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

    @Query("SELECT * FROM budget_table")
    fun getBudget(): Flow<List<BudgetEntity>>

    @Query("SELECT COUNT(*) FROM categories_table")
    suspend fun getCategoryCount(): Int

    @Query("SELECT * FROM categories_table")
    fun getCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategories(categories: List<Category>)

    @Delete
    suspend fun deleteCategory(category: Category)

    // Insert a single expense
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseWithCategoryId)

    // Update an existing expense
    @Update
    suspend fun updateExpense(expense: ExpenseWithCategoryId)

    // Delete a single expense
    @Delete
    suspend fun deleteExpense(expense: ExpenseWithCategoryId)

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

    // Get total expenses for a specific month
    @Query("SELECT SUM(amount) FROM expenses_table WHERE substr(date, 1, 7) = :month")
    fun getTotalExpensesByMonth(month: String): Flow<Double>

}