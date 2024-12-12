package com.iquad.budgetit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.iquad.budgetit.storage.AppDatabase
import com.iquad.budgetit.storage.BudgetItRepository
import com.iquad.budgetit.storage.PreferencesManager
import com.iquad.budgetit.ui.theme.BudgetItTheme
import com.iquad.budgetit.utils.GlobalStaticMessage
import com.iquad.budgetit.viewmodel.BudgetItViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetItTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val storageManager = PreferencesManager.getInstance(this)
                    val startDestination = if(storageManager.isFirstLaunch()){
                        Screen.EnterBudget.route
                    } else {
                        Screen.HomeScreen.route
                    }

                    val appDatabase = AppDatabase.getDatabase(this)
                    val appDao = appDatabase.appDao()
                    val appRepository = BudgetItRepository(appDao)
                    val viewModel = BudgetItViewModel(appRepository)

                    BudgetItNavHost(
                        innerPadding = innerPadding,
                        startDestination = startDestination,
                        viewModel = viewModel
                    )
                    GlobalStaticMessage.Display()
                }
            }
        }
    }
}