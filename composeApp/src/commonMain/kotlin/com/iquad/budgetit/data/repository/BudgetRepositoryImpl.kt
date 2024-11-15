package com.iquad.budgetit.data.repository

import androidx.datastore.core.DataStore
import com.iquad.budgetit.data.storage.BudgetPreferencesSerializer
import com.iquad.budgetit.domain.model.Budget
import com.iquad.budgetit.domain.repository.BudgetRepository
import com.iquad.budgetit.proto.BudgetPreferencesKt
import kotlinx.coroutines.flow.first

class BudgetRepositoryImpl(
    private val dataStore: DataStore<BudgetPreferencesKt>
) : BudgetRepository {

    override suspend fun saveBudget(budget: Budget): Result<Unit> = runCatching {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .setAmount(budget.amount)
                .setCurrency(budget.currency)
                .setIsBudgetSet(true)
                .build()
        }
    }

    override suspend fun getBudget(): Result<Budget?> = runCatching {
        val preferences = dataStore.data.first()
        if (preferences.isBudgetSet) {
            Budget(
                amount = preferences.amount,
                currency = preferences.currency
            )
        } else null
    }

    override suspend fun hasBudgetSet(): Result<Boolean> = runCatching {
        dataStore.data.first().isBudgetSet
    }
}
