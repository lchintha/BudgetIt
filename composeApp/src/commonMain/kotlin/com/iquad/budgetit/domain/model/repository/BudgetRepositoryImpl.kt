package com.iquad.budgetit.domain.model.repository

import com.iquad.budgetit.data.storage.Storage
import com.iquad.budgetit.domain.model.Budget
import com.iquad.budgetit.domain.model.Currency

/**
 * This class implements the BudgetRepository interface from the domain layer.
 * It handles the actual data operations using the Storage interface for persistence.
 */
class BudgetRepositoryImpl(
    private val storage: Storage
) : BudgetRepository {

    override suspend fun saveBudget(budget: Budget) {
        storage.putDouble(KEY_BUDGET_AMOUNT, budget.amount)
        storage.putString(KEY_BUDGET_CURRENCY, budget.currency.code)
    }

    override suspend fun getBudget(): Budget? {
        val amount = storage.getDouble(KEY_BUDGET_AMOUNT, -1.0)
        if (amount == -1.0) return null
        val currencyCode = storage.getString(KEY_BUDGET_CURRENCY, Currency.DEFAULT.code)
        val currency =
            Currency.SUPPORTED_CURRENCIES.find { it.code == currencyCode } ?: Currency.DEFAULT
        return Budget(amount, currency)
    }

    override suspend fun hasBudgetSet(): Boolean {
        return getBudget() != null
    }

    companion object {
        /**
         * Key used to store the budget amount in the Storage
         */
        private const val KEY_BUDGET_AMOUNT = "budget_amount"

        /**
         * Key used to store the currency symbol in the Storage
         */
        private const val KEY_BUDGET_CURRENCY = "budget_currency"
    }
}