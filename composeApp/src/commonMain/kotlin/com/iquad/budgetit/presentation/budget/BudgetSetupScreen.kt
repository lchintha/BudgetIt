package com.iquad.budgetit.presentation.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject

@Composable
fun BudgetSetupScreen(
    onNavigateNext: () -> Unit
) {
    val viewModel = koinInject<BudgetSetupViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isBudgetSet) {
        if (state.isBudgetSet) {
            onNavigateNext()
        }
    }

    BudgetSetupContent(
        state = state,
        onAmountChanged = viewModel::onAmountChanged,
        onCurrencySelected = viewModel::onCurrencySelected,
        onContinueClicked = viewModel::onContinueClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BudgetSetupContent(
    state: BudgetSetupState,
    onAmountChanged: (String) -> Unit,
    onCurrencySelected: (String) -> Unit,
    onContinueClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Welcome to Budget It",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Setting a monthly budget helps you track spending and reach your financial goals",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = { }
            ) {
                TextField(
                    value = state.selectedCurrency,
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier.menuAnchor(
                        type = MenuAnchorType.PrimaryEditable,
                        enabled = true
                    )
                )

                ExposedDropdownMenu(
                    expanded = false,
                    onDismissRequest = { }
                ) {
                    listOf("$", "€", "£", "¥").forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = { onCurrencySelected(currency) }
                        )
                    }
                }
            }

            TextField(
                value = state.amount,
                onValueChange = onAmountChanged,
                label = { Text("Enter budget") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }

        if (state.isError) {
            Text(
                text = state.errorMessage ?: "An error occurred",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinueClicked,
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading && state.amount.isNotEmpty() && state.amount != "0"
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Continue")
            }
        }
    }
}