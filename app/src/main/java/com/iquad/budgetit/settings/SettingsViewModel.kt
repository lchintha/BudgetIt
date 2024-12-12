package com.iquad.budgetit.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.iquad.budgetit.storage.AppDao
import com.iquad.budgetit.storage.AppDatabase
import com.iquad.budgetit.storage.Budget
import kotlinx.coroutines.flow.Flow

class SettingsViewModel(
    application: Application
): AndroidViewModel(application) {

    private val appDao: AppDao = AppDatabase.getDatabase(application).appDao()
    val budget: Flow<Budget> = appDao.getBudget()

}