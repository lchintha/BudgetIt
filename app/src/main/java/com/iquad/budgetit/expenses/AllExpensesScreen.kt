package com.iquad.budgetit.expenses

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iquad.budgetit.R
import com.iquad.budgetit.Screen
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.storage.Expense
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.utils.FlexibleAlertDialog
import com.iquad.budgetit.utils.toComposeColor
import com.iquad.budgetit.viewmodel.BudgetItViewModel
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun AllExpensesScreen(
    navController: NavController,
    viewModel: BudgetItViewModel,
    categoryId: Int?
) {
    LaunchedEffect(key1 = true) {
        if (categoryId != null) {
            viewModel.filterExpensesByCategoryInTimeFrame(categoryId)
        } else {
            viewModel.getExpensesForCurrentMonth()
        }
    }
    val expenses by if (categoryId != null) {
        viewModel.expensesByCategoryInTimeFrame.collectAsState()
    } else {
        viewModel.expenses.collectAsState()
    }
    val budget by viewModel.budgetState.collectAsState()
    var revealedItemId by remember { mutableStateOf<Int?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            BudgetItToolBar(
                title = stringResource(R.string.all_expenses),
                onBackPress = { navController.popBackStack() }
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
                        SwipeableExpenseItem(
                            expense,
                            currency = budget?.currency ?: Currency.USD,
                            onDelete = {
                                showDialog = true
                            },
                            onEdit = {
                                navController.navigate(Screen.AddExpenseScreen.createRoute(expense.data.id))
                            },
                            isRevealed = revealedItemId == expense.data.id,
                            onReveal = { revealed ->
                                revealedItemId = if (revealed) expense.data.id else null
                            }
                        )
                    }
                }
            }
        }
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
                revealedItemId?.let { viewModel.deleteExpense(it) }
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}

@Composable
fun SwipeableExpenseItem(
    expense: Expense,
    currency: Currency,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    isRevealed: Boolean,
    onReveal: (Boolean) -> Unit
) {
    val direction = remember { Animatable(0f) }
    val width = 50.dp
    val coroutineScope = rememberCoroutineScope()
    val totalActionWidth = (width.value * 2) + 8

    LaunchedEffect(isRevealed) {
        if (!isRevealed) {
            direction.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Background actions (Delete and Edit)
        Row(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onEdit,
                modifier = Modifier
                    .size(width)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(width)
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }

        // Main expense item content
        Box(
            modifier = Modifier
                .offset { IntOffset(direction.value.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            // Limit the drag based on current state
                            val newValue = direction.value + delta
                            val targetValue = when {
                                // If revealed, allow only positive (right) movement up to 0
                                isRevealed && newValue > 0 -> 0f
                                // If revealed, maintain minimum position
                                isRevealed -> maxOf(newValue, -totalActionWidth * 4)
                                // If not revealed, don't allow positive movement
                                newValue > 0 -> 0f
                                // If not revealed, limit negative movement
                                else -> maxOf(newValue, -totalActionWidth * 4)
                            }
                            direction.snapTo(targetValue)
                        }
                    },
                    onDragStarted = { },
                    onDragStopped = {
                        coroutineScope.launch {
                            val velocity = it
                            // Determine target value based on current position and velocity
                            val targetValue = when {
                                // If swiping right when revealed, close it
                                isRevealed && direction.value > -totalActionWidth * 3 -> 0f
                                // If swiping left far enough, reveal options
                                abs(direction.value) > totalActionWidth / 3 -> -totalActionWidth * 4
                                // Otherwise, return to closed state
                                else -> 0f
                            }

                            direction.animateTo(
                                targetValue = targetValue,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessMedium,
                                    visibilityThreshold = 0.1f
                                ),
                                initialVelocity = velocity
                            )
                            onReveal(targetValue != 0f)
                        }
                    }
                )
        ) {
            ExpenseItem(expense = expense, currency = currency)
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
                    text = expense.data.title,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
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
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}