package com.iquad.budgetit.di

import com.iquad.budgetit.data.storage.Storage
import org.koin.dsl.module
import android.content.Context

actual fun platformModule(context: Context) = module {
    single { Storage(context) }
}