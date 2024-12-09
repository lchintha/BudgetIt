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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.iquad.budgetit.R
import com.iquad.budgetit.model.Category
import com.iquad.budgetit.model.Expense
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.utils.CategoryColor
import com.iquad.budgetit.utils.CategoryIcon
import com.iquad.budgetit.utils.toComposeColor

@Composable
fun AllExpensesScreen(
    navController: NavController
) {
    val expenses = getListOfExpenses().toMutableList()
    expenses.addAll(getListOfExpenses())

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
                        ExpenseItem(expense)
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(
    expense: Expense
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
                    text = expense.title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = expense.date,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "$${expense.amount}",
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

fun getListOfExpenses(): List<Expense> {
    return listOf(
        Expense(
            id = 1,
            amount = 97.56,
            date = "13 Nov 2024",
            title = "Amazon",
            category = Category(
                id = 1,
                name = "Food",
                icon = CategoryIcon.RESTAURANTS,
                color = CategoryColor.GOLDENROD
            ),
        ),
        Expense(
            id = 1,
            amount = 10.56,
            date = "13 Nov 2024",
            title = "Amazon",
            category = Category(
                id = 1,
                name = "Food",
                icon = CategoryIcon.VACATION,
                color = CategoryColor.PINK
            ),
        ),
        Expense(
            id = 1,
            amount = 12.56,
            date = "13 Nov 2024",
            title = "Amazon",
            category = Category(
                id = 1,
                name = "Food",
                icon = CategoryIcon.GROCERY,
                color = CategoryColor.SKY_BLUE
            ),
        ),
        Expense(
            id = 1,
            amount = 105.89,
            date = "13 Nov 2024",
            title = "Amazon",
            category = Category(
                id = 1,
                name = "Food",
                icon = CategoryIcon.EDUCATION,
                color = CategoryColor.CORNFLOWER_BLUE
            ),
        )
    )
}

@Preview
@Composable
fun AllExpensesScreenPreview() {
    AllExpensesScreen(navController = rememberNavController())
}
