package com.iquad.budgetit.storage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.utils.CategoryColor
import com.iquad.budgetit.utils.CategoryIcon

@Entity(tableName = "budget_table")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val currency: Currency,
    val amount: Double,
)

@Entity(tableName = "categories_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val icon: CategoryIcon,
    val color: CategoryColor
)
