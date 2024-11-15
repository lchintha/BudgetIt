package com.iquad.budgetit

import android.app.Application
import com.iquad.budgetit.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.iquad.budgetit.di.platformModule

class BudgetItApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BudgetItApplication)
            modules(
                coreModule(),
                platformModule(this@BudgetItApplication)
            )
        }
    }
}