package com.iquad.budgetit.storage

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.utils.CategoryColor
import com.iquad.budgetit.utils.CategoryIcon

@Entity(tableName = "budget_table")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val currency: Currency,
    val amount: Double,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "categories_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val icon: CategoryIcon,
    val color: CategoryColor,
)

@Entity(tableName = "expenses_table")
data class ExpenseWithCategoryId(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val date: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Int
)

data class Expense(
    @Embedded val data: ExpenseWithCategoryId,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: Category
)
