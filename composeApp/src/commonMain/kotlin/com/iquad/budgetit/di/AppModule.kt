package com.iquad.budgetit.di

/**
 * This file contains all the Koin modules for dependency injection
 */
import com.iquad.budgetit.domain.model.repository.BudgetRepository
import com.iquad.budgetit.domain.model.repository.BudgetRepositoryImpl
import com.iquad.budgetit.domain.model.usecase.GetBudgetStatusUseCase
import com.iquad.budgetit.domain.model.usecase.SetBudgetUseCase
import com.iquad.budgetit.presentation.welcome.WelcomeScreenViewModel
import org.koin.dsl.module

/**
 * Core module containing platform-independent dependencies
 */
fun coreModule() = module {
    // Repositories
    single<BudgetRepository> { BudgetRepositoryImpl(get()) }

    // Use Cases
    factory { SetBudgetUseCase(get()) }
    factory { GetBudgetStatusUseCase(get()) }

    // ViewModels
    factory {
        WelcomeScreenViewModel(
            setBudgetUseCase = get(),
            getBudgetStatusUseCase = get()
        )
    }
}