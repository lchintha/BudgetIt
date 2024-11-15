package com.iquad.budgetit

import androidx.compose.ui.window.ComposeUIViewController
import com.iquad.budgetit.di.coreModule
import com.iquad.budgetit.di.platformModule
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