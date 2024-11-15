package com.iquad.budgetit.data.storage

import androidx.datastore.core.DataStore
import com.iquad.budgetit.data.proto.BudgetPreferences

expect fun createDataStore(context: Any): DataStore<BudgetPreferences>