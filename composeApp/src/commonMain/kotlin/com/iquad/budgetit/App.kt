package com.iquad.budgetit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import moe.tlaster.precompose.PreComposeApplication
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

import budgetit.composeapp.generated.resources.Res
import budgetit.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navigator = rememberNavigator()
        PreComposeApplication {
            NavHost(
                navigator = navigator,
                initialRoute = "welcome",
                transition = NavTransition()
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