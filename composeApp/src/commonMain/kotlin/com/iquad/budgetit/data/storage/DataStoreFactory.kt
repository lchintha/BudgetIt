package com.iquad.budgetit.data.storage

import androidx.datastore.core.DataStore
import com.iquad.budgetit.proto.BudgetPreferencesKt

expect fun createDataStore(context: Any): DataStore<BudgetPreferencesKt>
