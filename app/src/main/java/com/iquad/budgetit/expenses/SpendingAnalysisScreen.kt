package com.iquad.budgetit.expenses

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.RequestQuote
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iquad.budgetit.R
import com.iquad.budgetit.charts.ExpensesBarGraph
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.model.ExpenseBreakDown
import com.iquad.budgetit.model.TabItem
import com.iquad.budgetit.model.TimeFrame
import com.iquad.budgetit.settings.TitleText
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.utils.roundToTwoDecimalPlaces
import com.iquad.budgetit.utils.toComposeColor
import com.iquad.budgetit.viewmodel.BudgetItViewModel

@Composable
fun SpendingAnalysisScreen(
    navController: NavController,
    viewModel: BudgetItViewModel
) {
    LaunchedEffect(key1 = true) {
        viewModel.updateExpensesListForSelectedTimeFrame()
    }

    val currency by viewModel.budgetState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val selectedTimeFrame by viewModel.selectedTimeFrame.collectAsState()
    val expensesByTimeFrame by viewModel.expensesByTimeFrame.collectAsState()
    val expensesByCategory by viewModel.expensesByCategory.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Column {
                BudgetItToolBar(
                    title = stringResource(R.string.spending_analysis),
                    onBackPress = { navController.popBackStack() }
                )
            }
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                IntervalTabBar(
                    selectedTab,
                    onClick = {
                        viewModel.setSelectedTab(it)
                        viewModel.setOrUpdateTimeFrame()
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TimeFrameTabBar(
                    selectedTimeFrame = selectedTimeFrame,
                    onChangeTimeFrameClick = {
                        viewModel.setOrUpdateTimeFrame(
                            isInitialSetup = false,
                            toPrevious = it
                        )
                    }
                )
                if (expensesByCategory.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ExpensesBarGraph(
                        expenses = expensesByTimeFrame,
                        startDate = selectedTimeFrame.startDate,
                        endDate = selectedTimeFrame.endDate,
                        tabItem = selectedTab
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TitleText(
                        title = stringResource(R.string.category_breakdown),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column {
                        LazyColumn(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(expensesByCategory) { expense ->
                                ExpensesByCategoryItem(
                                    expense,
                                    currency = currency?.currency ?: Currency.USD
                                )
                            }
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(24.dp))
                    NoExpensesToDisplay(
                        stringResource(R.string.no_expenses_to_display)
                    )
                }
            }
            if (expensesByCategory.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    TotalAmountSection(
                        expensesByCategory.sumOf { it.amount }
                    )
                }
            }
        }
    }
}

@Composable
fun IntervalTabBar(
    selectedTab: TabItem,
    onClick: (item: TabItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TabItem(
            title = "Weekly",
            isSelected = selectedTab == TabItem.Weekly,
            onClick = {
                onClick(TabItem.Weekly)
            }
        )
        TabItem(
            title = "Monthly",
            isSelected = selectedTab == TabItem.Monthly,
            onClick = {
                onClick(TabItem.Monthly)
            }
        )
        TabItem(
            title = "Yearly",
            isSelected = selectedTab == TabItem.Yearly,
            onClick = {
                onClick(TabItem.Yearly)
            }
        )
    }
}

@Composable
private fun TabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val primaryColor = colorResource(R.color.colorPrimary)
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = null,
                indication = null
            ) { onClick() }
            .background(
                if (isSelected) primaryColor else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.White else primaryColor,
            style = TextStyle(fontWeight = FontWeight.Medium)
        )
    }
}

@Composable
fun TimeFrameTabBar(
    selectedTimeFrame: TimeFrame,
    onChangeTimeFrameClick: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Previous",
                tint = Color.Gray,
                modifier = Modifier.clickable {
                    onChangeTimeFrameClick(true)
                }
            )
            Text(
                text = selectedTimeFrame.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next",
                tint = Color.Gray,
                modifier = Modifier.clickable {
                    onChangeTimeFrameClick(false)
                }
            )
        }
    }
}

@Composable
fun ExpensesByCategoryItem(
    expense: ExpenseBreakDown,
    currency: Currency = Currency.USD
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
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
                    text = expense.category.name,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${currency.symbol}${expense.amount.roundToTwoDecimalPlaces()}",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(
                        color = colorResource(R.color.colorPrimary),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Text(
                    text = expense.percentage.toInt().toString() + "%",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun TotalAmountSection(
    amount: Double
) {
    Row(
        modifier = Modifier
            .background(
                color = colorResource(R.color.colorPrimary),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(5.dp)
    ) {
        Text(
            text = stringResource(R.string.total_spending),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White
            ),
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
        )
        Text(
            text = "${Currency.USD.symbol}${amount.roundToTwoDecimalPlaces()}",
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White
            ),
            modifier = Modifier
                .padding(10.dp)
        )
    }
}

@Composable
fun NoExpensesToDisplay(
    text: String
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.RequestQuote,
                contentDescription = "No Expenses",
                tint = Color.Gray,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun NoExpensesToDisplayPreview() {
    NoExpensesToDisplay(
        "No expenses to display in the selected timeframe. Please select different dates to see the spending analysis."
    )
}