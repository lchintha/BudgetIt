package com.iquad.budgetit.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.iquad.budgetit.proto.BudgetPreferencesKt
import java.io.File

actual class DataStoreProvider(
    private val context: Context
) {
    actual fun provideDataStore(): DataStore<BudgetPreferencesKt> {
        return DataStoreFactory.create(
            serializer = BudgetPreferencesSerializer(),
            produceFile = {
                File(context.filesDir, "budget_preferences.pb").apply {
                    if (!exists()) createNewFile()
                }
            }
        )
    }
}
