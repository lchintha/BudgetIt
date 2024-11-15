package com.iquad.budgetit.di

import com.iquad.budgetit.data.repository.BudgetRepositoryImpl
import com.iquad.budgetit.data.storage.createDataStore
import com.iquad.budgetit.domain.repository.BudgetRepository
import com.iquad.budgetit.presentation.budget.BudgetSetupViewModel
import org.koin.dsl.module

val appModule = module {
    single { createDataStore(get()) }
    single<BudgetRepository> { BudgetRepositoryImpl(get()) }
    factory { BudgetSetupViewModel(get()) }
}