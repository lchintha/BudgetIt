import org.koin.dsl.module

actual fun platformModule() = module {
    single { Storage() }
}