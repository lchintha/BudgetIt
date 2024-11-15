package com.iquad.budgetit.presentation.budget

// Path: composeApp/src/commonMain/kotlin/com/yourpackage/presentation/budget/BudgetSetupViewModel.kt
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.iquad.budgetit.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BudgetSetupViewModel(
    private val budgetRepository: BudgetRepository
) : ScreenModel {
    private val _state = MutableStateFlow(BudgetSetupState())
    val state = _state.asStateFlow()

    init {
        checkBudgetStatus()
    }

    private fun checkBudgetStatus() {
        coroutineScope.launch {
            budgetRepository.hasBudgetSet()
                .onSuccess { isBudgetSet ->
                    _state.update { it.copy(isBudgetSet = isBudgetSet) }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isError = true,
                            errorMessage = error.message ?: "Failed to check budget status"
                        )
                    }
                }
        }
    }

    fun onAmountChanged(amount: String) {
        if (amount.isEmpty() || amount.matches(Regex("^\\d*\\.?\\d*$"))) {
            _state.update { it.copy(amount = amount, isError = false, errorMessage = null) }
        }
    }

    fun onCurrencySelected(currency: String) {
        _state.update { it.copy(selectedCurrency = currency) }
    }

    fun onContinueClicked() {
        coroutineScope.launch {
            _state.update { it.copy(isLoading = true, isError = false, errorMessage = null) }

            val amount = _state.value.amount.toDoubleOrNull() ?: 0.0
            val budget = Budget(amount, _state.value.selectedCurrency)

            budgetRepository.saveBudget(budget)
                .onSuccess {
                    _state.update { it.copy(isBudgetSet = true) }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isError = true,
                            errorMessage = error.message ?: "Failed to save budget"
                        )
                    }
                }

            _state.update { it.copy(isLoading = false) }
        }
    }
}