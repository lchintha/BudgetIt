package com.iquad.budgetit.data.storage

import androidx.datastore.core.DataStore
import com.iquad.budgetit.data.proto.BudgetPreferences

expect class DataStoreProvider {
    fun provideDataStore(): DataStore<BudgetPreferences>
}