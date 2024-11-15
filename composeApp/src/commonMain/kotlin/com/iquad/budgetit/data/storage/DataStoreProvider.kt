package com.iquad.budgetit.data.storage

import androidx.datastore.core.DataStore
import com.iquad.budgetit.proto.BudgetPreferencesKt

expect class DataStoreProvider {
    fun provideDataStore(): DataStore<BudgetPreferencesKt>
}
