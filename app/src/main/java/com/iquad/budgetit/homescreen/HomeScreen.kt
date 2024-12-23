package com.iquad.budgetit.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iquad.budgetit.R
import com.iquad.budgetit.Screen
import com.iquad.budgetit.charts.HalfCircleProgressBar
import com.iquad.budgetit.expenses.NoExpensesToDisplay
import com.iquad.budgetit.expenses.SwipeableExpenseItem
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.storage.Expense
import com.iquad.budgetit.utils.FlexibleAlertDialog
import com.iquad.budgetit.viewmodel.BudgetItViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: BudgetItViewModel
) {
    LaunchedEffect(key1 = true) {
        viewModel.getExpensesForCurrentMonth()
    }
    val expenses by viewModel.expenses.collectAsState()
    val totalExpenses by viewModel.totalExpenses.collectAsState()
    val budget by viewModel.budgetState.collectAsState()
    val revealedItemId = remember { mutableStateOf<Int?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        HomeScreenToolBar(navController)
        Spacer(modifier = Modifier.height(8.dp))
        HalfCircleProgressBar(
            spentAmount = totalExpenses,
            totalAmount = budget?.amount ?: 1.0,
            currency = budget?.currency ?: Currency.USD,
            onClickListener = {
                navController.navigate(Screen.SpendingAnalysisScreen.route)
            }
        )
        AddExpenseButton(navController)
        Spacer(modifier = Modifier.height(8.dp))
        RecentExpenses(
            allExpenses = expenses,
            currency = budget?.currency ?: Currency.USD,
            onAllLinkClickListener = {
                navController.navigate(Screen.AllExpensesScreen.route)
            },
            onDeleteClickListener = {
                showDialog = true
            },
            onEditClickListener = {
                navController.navigate(Screen.AddExpenseScreen.createRoute(revealedItemId.value))
            },
            revealedItemId = revealedItemId
        )
    }

    if (showDialog) {
        FlexibleAlertDialog(
            description = stringResource(R.string.delete_expense),
            primaryActionText = stringResource(R.string.cancel),
            secondaryActionText = stringResource(R.string.confirm),
            primaryActionColor = MaterialTheme.colorScheme.primary,
            secondaryActionColor = Color.Red,
            onPrimaryAction = {
                showDialog = false
            },
            onSecondaryAction = {
                revealedItemId.value?.let { itemId -> viewModel.deleteExpense(itemId) }
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}

@Composable
fun HomeScreenToolBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.budget_tracker),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        IconButton(onClick = {
            navController.navigate(Screen.SettingsScreen.route)
        }) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings"
            )
        }
    }
}

@Composable
fun AddExpenseButton(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .clickable {
                navController.navigate(Screen.AddExpenseScreen.createRoute())
            }
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    )
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    modifier = Modifier
                        .padding(10.dp),
                    contentDescription = "Add Button"
                )
            }
            Text(
                text = stringResource(R.string.add_expense),
                modifier = Modifier
                    .padding(start = 18.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Composable
fun RecentExpenses(
    allExpenses: List<Expense>,
    currency: Currency,
    onAllLinkClickListener: () -> Unit = {},
    onDeleteClickListener: () -> Unit,
    onEditClickListener: () -> Unit,
    revealedItemId: MutableState<Int?>
) {
    val expenses = allExpenses.sortedByDescending { it.data.date }.take(3)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.recent_expenses),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            if(expenses.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.all),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = colorResource(R.color.colorPrimary)
                    ),
                    modifier = Modifier
                        .clickable {
                            onAllLinkClickListener.invoke()
                        }
                        .padding(4.dp)
                )
            }
        }
        if(expenses.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(expenses) { expense ->
                    SwipeableExpenseItem(
                        expense,
                        currency = currency,
                        onDelete = onDeleteClickListener,
                        onEdit = onEditClickListener,
                        isRevealed = revealedItemId.value == expense.data.id,
                        onReveal = { revealed ->
                            revealedItemId.value = if (revealed) expense.data.id else null
                        }
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            NoExpensesToDisplay(
                stringResource(R.string.no_recent_expenses)
            )
        }
    }
}