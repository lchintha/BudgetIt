package com.iquad.budgetit.data.storage

import com.iquad.budgetit.proto.BudgetPreferencesKt
import okio.BufferedSink
import okio.BufferedSource

class BudgetPreferencesSerializer : Serializer<BudgetPreferencesKt> {
    override val defaultValue: BudgetPreferencesKt = BudgetPreferencesKt.getDefaultInstance()

    override suspend fun readFrom(source: BufferedSource): BudgetPreferencesKt {
        return try {
            BudgetPreferencesKt.parseFrom(source.readByteArray())
        } catch (exception: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: BudgetPreferencesKt, sink: BufferedSink) {
        sink.write(t.toByteArray())
    }
}
