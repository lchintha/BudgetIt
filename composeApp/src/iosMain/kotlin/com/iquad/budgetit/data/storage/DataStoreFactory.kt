package com.iquad.budgetit.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.iquad.budgetit.proto.BudgetPreferencesKt
import okio.FileSystem
import okio.Path.Companion.toPath

actual fun createDataStore(context: Any): DataStore<BudgetPreferencesKt> {
    val fileSystem = FileSystem.SYSTEM
    val documentsPath = NSSearchPathForDirectoriesInDomains(
        NSDocumentDirectory,
        NSUserDomainMask,
        true
    ).first() as String

    return DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = fileSystem,
            producePath = "$documentsPath/budget_preferences.pb".toPath(),
        ),
        serializer = BudgetPreferencesSerializer()
    )
}
