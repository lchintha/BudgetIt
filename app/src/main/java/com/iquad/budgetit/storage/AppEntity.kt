package com.iquad.budgetit.storage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iquad.budgetit.model.Currency

@Entity(tableName = "budget_table")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val currency: Currency,
    val amount: Double,
)
