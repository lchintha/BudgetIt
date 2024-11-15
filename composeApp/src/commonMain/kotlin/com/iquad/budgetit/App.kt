package com.iquad.budgetit

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.iquad.budgetit.presentation.welcome.WelcomeScreen
import com.iquad.budgetit.presentation.welcome.WelcomeScreenViewModel
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navigator = rememberNavigator()
         {
            NavHost(
                navigator = navigator,
                initialRoute = "welcome",
                navTransition = NavTransition()
            ) {
                scene("welcome") {
                    // Use koinInject() to get ViewModel instance
                    val viewModel = koinInject<WelcomeScreenViewModel>()

                    WelcomeScreen(
                        viewModel = viewModel,
                        onNavigateToHome = {
                            navigator.navigate("home")
                        }
                    )
                }

                scene("home") {
                    // Home screen implementation
                }
            }
        }
    }
}