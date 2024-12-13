package com.iquad.budgetit.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iquad.budgetit.R
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.storage.Expense
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.utils.toComposeColor
import com.iquad.budgetit.viewmodel.BudgetItViewModel

@Composable
fun AllExpensesScreen(
    navController: NavController,
    viewModel: BudgetItViewModel
) {
    LaunchedEffect(key1 = true) {
        viewModel.getExpensesForCurrentMonth()
    }
    val expenses by viewModel.expenses.collectAsState()
    val budget by viewModel.budgetState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            BudgetItToolBar(
                navController = navController,
                title = stringResource(R.string.all_expenses),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(expenses) { expense ->
                        ExpenseItem(
                            expense,
                            currency = budget?.currency ?: Currency.USD
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(
    expense: Expense,
    currency: Currency
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = colorResource(R.color.light_blue)
            )
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(expense.category.color.hex.toComposeColor())
                    .border(
                        width = 3.dp,
                        color = Color.Transparent,
                        shape = CircleShape
                    )
                    .graphicsLayer {
                        scaleX = 1f
                        scaleY = 1f
                    },
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = expense.category.icon.imageVector,
                    contentDescription = expense.category.name,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = expense.data.title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = expense.data.date,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${currency.symbol}${expense.data.amount}",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}
