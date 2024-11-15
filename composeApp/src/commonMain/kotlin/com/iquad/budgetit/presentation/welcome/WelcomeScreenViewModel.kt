package com.iquad.budgetit.presentation.welcome

import androidx.lifecycle.ViewModel
import com.iquad.budgetit.domain.model.Currency
import com.iquad.budgetit.domain.model.usecase.GetBudgetStatusUseCase
import com.iquad.budgetit.domain.model.usecase.SetBudgetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The ViewModel for the welcome screen. It handles the business logic and state management
 * for the welcome screen UI. It communicates with the domain layer through use cases
 * and exposes state through StateFlow.
 */
class WelcomeScreenViewModel(
    private val setBudgetUseCase: SetBudgetUseCase,
    private val getBudgetStatusUseCase: GetBudgetStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WelcomeScreenState())
    val state: StateFlow<WelcomeScreenState> = _state.asStateFlow()

    init {
        // Check if budget is already set on initialization
        viewModelScope.launch {
            val hasBudget = getBudgetStatusUseCase()
            if (hasBudget) {
                _state.update { it.copy(shouldNavigateToHome = true) }
            }
        }
    }

    /**
     * Handles budget text input changes
     * Filters out non-numeric characters except decimal point
     */
    fun onBudgetChanged(newValue: String) {
        val numericValue = newValue.filter { it.isDigit() || it == '.' }
        _state.update {
            it.copy(
                budgetText = numericValue,
                showError = false
            )
        }
    }

    /**
     * Handles currency symbol changes
     */
    fun onCurrencySelected(currency: Currency) {
        _state.update { it.copy(selectedCurrency = currency) }
    }

    /**
     * Handles whether the drop down expansion state changes
     */
    fun onDropdownExpandedChange(expanded: Boolean) {
        _state.update { it.copy(isDropdownExpanded = expanded) }
    }

    /**
     * Handles the continue button click
     * Validates input and saves budget if valid
     */
    fun onContinueClicked() {
        viewModelScope.launch {
            try {
                val budget = state.value.budgetText.toDoubleOrNull()

                if (budget != null && budget > 0) {
                    setBudgetUseCase(
                        amount = budget,
                        currency = state.value.selectedCurrency
                    )
                    _state.update { it.copy(shouldNavigateToHome = true) }
                } else {
                    _state.update { it.copy(showError = true) }
                }
            } catch (e: Exception) {
                // Handle storage errors
                _state.update {
                    it.copy(
                        showError = true
                    )
                }
            }
        }
    }
}