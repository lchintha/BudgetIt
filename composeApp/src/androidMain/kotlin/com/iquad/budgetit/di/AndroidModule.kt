package com.iquad.budgetit.di

import android.content.Context
import com.iquad.budgetit.data.storage.Storage
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(context: Context): Module = module {
    single<Storage> { Storage(context) }
}