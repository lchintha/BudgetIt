package com.iquad.budgetit.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.storage.BudgetEntity
import com.iquad.budgetit.storage.BudgetItRepository
import com.iquad.budgetit.storage.Category
import com.iquad.budgetit.storage.PreferencesManager
import com.iquad.budgetit.storage.defaultstorage.CategoryInitializer
import com.iquad.budgetit.utils.CategoryColor
import com.iquad.budgetit.utils.CategoryIcon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BudgetItViewModel(
    private val repository: BudgetItRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _budgetState = MutableStateFlow<BudgetEntity?>(null)
    val budgetState: StateFlow<BudgetEntity?> get() = _budgetState

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    init {
        viewModelScope.launch {
            repository.budget.collect { budget ->
                if (budget.isNotEmpty())
                    _budgetState.value = budget[0]
            }
        }
    }

    fun processBudget(
        context: Context,
        currency: Currency,
        amount: Double
    ) {
        viewModelScope.launch {
            if (amount == 0.0) {
                _uiState.value = UiState.Error
            } else {
                val storageManager = PreferencesManager.getInstance(context)
                repository.insertBudget(
                    BudgetEntity(
                        currency = currency,
                        amount = amount
                    )
                )
                CategoryInitializer.initializeCategories(repository)
                storageManager.setFirstLaunch(false)
                _uiState.value = UiState.Success
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            repository.categories.collect { categories ->
                _categories.value = categories
            }
        }
    }

    fun addCategory(
        name: String,
        color: CategoryColor?,
        icon: CategoryIcon?
    ) {
        viewModelScope.launch {
            if (name.isEmpty() || color == null || icon == null) {
                _uiState.value = UiState.Error
            } else {
                repository.insertCategory(
                    Category(
                        name = name,
                        color = color,
                        icon = icon
                    )
                )
                _uiState.value = UiState.Success
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            repository.deleteCategory(category)
            _categories.update { currentCategories ->
                currentCategories.filter { it.id != category.id }
            }
        }
    }

    sealed class UiState {
        data object Success : UiState()
        data object Error : UiState()
    }
}