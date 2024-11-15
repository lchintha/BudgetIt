package com.iquad.budgetit

import com.yourapp.budgetit.data.datastore.IosDataStoreProvider
import com.iquad.budgetit.di.appModule
import com.iquad.budgetit.di.IosModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule, IosMo)
    }
}

fun MainViewController() = ComposeUIViewController { App() }