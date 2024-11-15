package com.iquad.budgetit.data.storage

import androidx.datastore.core.DataStore

expect class DataStoreProvider {
    fun provideDataStore(): DataStore<BudgetPreferences>
}