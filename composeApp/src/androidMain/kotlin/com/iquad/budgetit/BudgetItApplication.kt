import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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