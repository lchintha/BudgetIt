package com.iquad.budgetit.storage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iquad.budgetit.model.Currency

@Entity(tableName = "budget")
data class Budget(
    val currency: Currency,
    val amount: Double,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
