package com.iquad.budgetit.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}