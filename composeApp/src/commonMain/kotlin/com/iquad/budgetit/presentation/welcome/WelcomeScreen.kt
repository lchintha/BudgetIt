package com.iquad.budgetit.presentation.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iquad.budgetit.presentation.welcome.components.BudgetInput

/**
 * The main welcome screen composable. This is the first screen users see when they
 * haven't set a budget yet. It combines all the UI components and handles navigation.
 */
@Composable
fun WelcomeScreen(
    viewModel: WelcomeScreenViewModel,
    onNavigateToHome: () -> Unit
) {
    // Collect UI state
    val state by viewModel.state.collectAsState()

    // Handle navigation
    LaunchedEffect(state.shouldNavigateToHome) {
        if (state.shouldNavigateToHome) {
            onNavigateToHome()
        }
    }

    // Screen content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .testTag("welcome_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Title
        Text(
            text = "Welcome to Budget It",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )

        // Subtitle
        Text(
            text = "Setting a monthly budget helps you track spending\n" +
                   "and reach your financial goals",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Budget input
        BudgetInput(
            value = state.budgetText,
            onValueChange = viewModel::onBudgetChanged,
            isError = state.showError
        )

        Text(
            text = "Enter budget",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Continue button
        Button(
            onClick = viewModel::onContinueClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Continue")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}