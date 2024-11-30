package com.iquad.budgetit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.utils.InputAmountTextField
import com.iquad.budgetit.utils.RegularTextField
import com.iquad.budgetit.model.Category
import com.iquad.budgetit.utils.CategoryIcon
import com.iquad.budgetit.utils.toComposeColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddExpenseScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 56.dp)
        ) {
            BudgetItToolBar(
                navController = navController,
                title = stringResource(R.string.add_expense),
                toolbarOption = stringResource(R.string.save)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                InputAmountTextField(
                    onValueChange = {}
                )
                Spacer(modifier = Modifier.height(8.dp))
                RegularTextField(
                    onValueChange = {},
                    placeholder = stringResource(R.string.whats_this_expense_for),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(16.dp))
                CategoriesTitle()
                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.weight(1f)) {
                    CategoriesList(
                        getCategories(),
                        onCategorySelected = {}
                    )
                }
            }
        }

        BottomCalendarSection(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentWidth()
                .padding(16.dp)
                .border(
                    BorderStroke(width = 1.dp, color = Color.Gray),
                    shape = RoundedCornerShape(8.dp)
                )
        )
    }
}

@Composable
fun CategoriesTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.category),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.edit),
            style = MaterialTheme.typography.titleMedium.copy(
                color = colorResource(R.color.colorPrimary)
            )
        )
    }
}

@Composable
fun CategoriesList(
    categories: List<Category>,
    onCategorySelected: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    isSelected = category == selectedCategory,
                    onCategoryClick = {
                        selectedCategory = category
                        onCategorySelected()
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onCategoryClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onCategoryClick
            )
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(category.color.toComposeColor())
                .border(
                    width = 3.dp,
                    color = if (isSelected) colorResource(R.color.colorPrimary) else Color.Transparent,
                    shape = CircleShape
                )
                .graphicsLayer {
                    if (isSelected) {
                        scaleX = 1.2f
                        scaleY = 1.2f
                    } else {
                        scaleX = 1f
                        scaleY = 1f
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = category.icon.imageVector,
                contentDescription = category.name,
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.name,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BottomCalendarSection(
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit = {}
) {
    val selectedDate by remember { mutableStateOf(LocalDate.now()) }
    Row(
        modifier = modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.CalendarMonth,
            contentDescription = "Calendar",
        )
        Text(
            text = selectedDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun AddExpenseScreenPreview() {
    AddExpenseScreen(rememberNavController())
}

fun getCategories(): List<Category> {
    val categories = listOf(
        Category(1, "Food", CategoryIcon.ADD, "#FF0000"),
        Category(2, "Travel", CategoryIcon.PERSON, "#00FF00"),
        Category(3, "Shopping", CategoryIcon.HOME, "#0000FF"),
        Category(4, "Entertainment", CategoryIcon.SETTINGS, "#FFFF00"),
        Category(5, "Other", CategoryIcon.SHOPPING, "#FF00FF"),
        Category(6, "Food", CategoryIcon.ADD, "#FF0000"),
        Category(7, "Travel", CategoryIcon.PERSON, "#00FF00")
    )
    return categories
}