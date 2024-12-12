package com.iquad.budgetit.enterbudget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iquad.budgetit.R
import com.iquad.budgetit.Screen
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.utils.CurrencyDropdown
import com.iquad.budgetit.utils.GlobalStaticMessage
import com.iquad.budgetit.utils.InputAmountTextField
import com.iquad.budgetit.utils.MessageType
import com.iquad.budgetit.viewmodel.BudgetItViewModel

@Composable
fun EnterBudgetScreen(
    navController: NavController,
    viewModel: BudgetItViewModel
) {
    val uiState by viewModel.uiState.observeAsState()

    val budgetAmount = remember { mutableStateOf("") }
    val selectedCurrency = remember { mutableStateOf(Currency.USD) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 60.dp)
            ) {
                Text(
                    text = stringResource(R.string.welcome_to_budget_it),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(R.string.enter_budget_description),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(48.dp))

                // Budget Input Section
                BudgetInputField(
                    budgetInput = budgetAmount,
                    selectedCurrency = selectedCurrency
                )
            }
        }

        // Continue Button
        Button(
            onClick = {
                viewModel.processBudget(
                    navController.context,
                    selectedCurrency.value,
                    if(budgetAmount.value.isEmpty()) 0.0 else budgetAmount.value.toDouble()
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 36.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.colorPrimary)
            )
        ) {
            Text(
                text = stringResource(R.string.btn_continue),
                color = Color.White,
            )
        }
    }

    uiState?.let {
        when (uiState) {
            is BudgetItViewModel.UiState.Success -> {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }

            BudgetItViewModel.UiState.Error -> {
                GlobalStaticMessage.show(
                    context = navController.context,
                    title = "Enter Budget",
                    messageType = MessageType.FAILURE
                )
            }

            else -> {}
        }
    }
}

@Composable
fun BudgetInputField(
    budgetInput: MutableState<String>,
    selectedCurrency: MutableState<Currency>
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .background(
                        color = Color(0xFFF0F0F0),
                        shape = CircleShape
                    )
                ) {
                    CurrencyDropdown(
                        selectedCurrency = selectedCurrency,
                        size = 18
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(0.5f),
                contentAlignment = Alignment.CenterStart
            ) {
                InputAmountTextField(
                    onValueChange = {
                        budgetInput.value = it
                    },
                    defaultAmount = budgetInput,
                    displayHint = false,
                    includeCurrencySymbol = false,
                    align = TextAlign.Start
                )
            }
        }
        Text(
            text = stringResource(R.string.enter_budget),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}
