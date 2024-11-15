package com.iquad.budgetit

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            coreModule(),
            platformModule()
        )
    }
}

fun MainViewController() = ComposeUIViewController { App() }