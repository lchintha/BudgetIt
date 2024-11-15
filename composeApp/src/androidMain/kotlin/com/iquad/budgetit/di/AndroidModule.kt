package com.iquad.budgetit.di

import android.content.Context
import com.iquad.budgetit.data.storage.DataStoreProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single {
        // Provide Android Context
        androidContext() as Context
    }

    // Android-specific DataStoreProvider
    single {
        DataStoreProvider(get())
    }
}