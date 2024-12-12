package com.iquad.budgetit.enterbudget

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.storage.AppDao
import com.iquad.budgetit.storage.Budget
import com.iquad.budgetit.storage.PreferencesManager
import kotlinx.coroutines.launch

class WelcomeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun processBudget(appDao: AppDao, currency: Currency, amount: Double) {
        viewModelScope.launch {
            if (amount == 0.0) {
                _uiState.value = UiState.Error
            } else {
                val storageManager = PreferencesManager.getInstance(getApplication())
                appDao.insertBudget(Budget(currency, amount))
                storageManager.setFirstLaunch(false)
                _uiState.value = UiState.Success
            }
        }
    }

    sealed class UiState {
        data object Success : UiState()
        data object Error : UiState()
    }
}