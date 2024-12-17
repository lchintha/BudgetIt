package com.iquad.budgetit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.iquad.budgetit.model.ThemeMode
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
            val storageManager = PreferencesManager.getInstance(this)
            val startDestination = if(storageManager.isFirstLaunch()){
                Screen.EnterBudget.route
            } else {
                Screen.HomeScreen.route
            }

            val appDatabase = AppDatabase.getDatabase(this)
            val appDao = appDatabase.appDao()
            val appRepository = BudgetItRepository(appDao)
            val viewModel = BudgetItViewModel(
                appRepository,
                application = application
            )
            val selectedTheme by viewModel.currentTheme.collectAsState()
            // Apply theme based on selection
            val isSystemDarkTheme = isSystemInDarkTheme()
            val isDarkMode = when (selectedTheme) {
                ThemeMode.Dark -> true
                ThemeMode.Light -> false
                ThemeMode.System -> isSystemDarkTheme
            }
            BudgetItTheme(
                darkTheme = isDarkMode
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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