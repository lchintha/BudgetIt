import com.iquad.budgetit.data.storage.Storage
import org.koin.dsl.module

actual fun platformModule() = module {
    single { Storage() }
}