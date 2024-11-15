/**
 * This file contains all the Koin modules for dependency injection
 */
import org.koin.core.module.Module
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