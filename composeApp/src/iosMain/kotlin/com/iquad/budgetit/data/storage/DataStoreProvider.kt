package com.iquad.budgetit.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.iquad.budgetit.data.proto.BudgetPreferences
import okio.FileSystem
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual class DataStoreProvider {
    actual fun provideDataStore(): DataStore<BudgetPreferences> {
        val fileSystem = FileSystem.SYSTEM
        val documentsPath = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        ).first() as String

        return DataStoreFactory.create(
            storage = OkioStorage(
                fileSystem = fileSystem,
                path = "$documentsPath/budget_preferences.pb".toPath()
            ),
            serializer = BudgetPreferencesSerializer()
        )
    }
}