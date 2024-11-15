package com.iquad.budgetit.data.storage

import androidx.datastore.core.Serializer
import com.iquad.budgetit.proto.BudgetPreferences
import okio.BufferedSink
import okio.BufferedSource

class BudgetPreferencesSerializer : Serializer<BudgetPreferences> {
    override val defaultValue: BudgetPreferences = BudgetPreferences.getDefaultInstance()

    override suspend fun readFrom(source: BufferedSource): BudgetPreferences {
        return try {
            BudgetPreferences.parseFrom(source.readByteArray())
        } catch (exception: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: BudgetPreferences, sink: BufferedSink) {
        sink.write(t.toByteArray())
    }
}