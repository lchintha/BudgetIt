package com.iquad.budgetit.di

import com.iquad.budgetit.data.storage.DataStoreProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single {
        // Provide Android Context
        androidContext()
    }

    // Android-specific DataStoreProvider
    single {
        DataStoreProvider(get())
    }
}
