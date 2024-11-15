package com.iquad.budgetit.domain.model.usecase

import com.iquad.budgetit.domain.model.Budget
import com.iquad.budgetit.domain.model.Currency
import com.iquad.budgetit.domain.model.repository.BudgetRepository

/**
 * This use case handles the business logic for setting a new budget. It encapsulates
 * the conversion from raw amount to com.iquad.budgetit.domain.model.Budget domain model and delegates storage to
 * the repository.
 */
class SetBudgetUseCase(private val repository: BudgetRepository) {
    /**
     * Sets a new budget with the given amount
     * @param amount The budget amount to set
     */
    suspend operator fun invoke(amount: Double, currency: Currency) {
        repository.saveBudget(Budget(amount, currency))
    }
}