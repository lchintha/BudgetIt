package com.iquad.budgetit

import com.iquad.budgetit.di.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule, IosMo)
    }
}

fun MainViewController() = ComposeUIViewController { App() }