package com.iquad.budgetit.expenses

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iquad.budgetit.R
import com.iquad.budgetit.Screen
import com.iquad.budgetit.storage.Category
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.utils.InputAmountTextField
import com.iquad.budgetit.utils.RegularTextField
import com.iquad.budgetit.utils.toComposeColor
import com.iquad.budgetit.viewmodel.BudgetItViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    navController: NavController,
    viewModel: BudgetItViewModel
) {
    LaunchedEffect(key1 = true) {
        viewModel.getCategories()
    }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var isEditable by remember { mutableStateOf(false) }
    val budgetAmount = remember { mutableStateOf("0") }
    val categories by viewModel.categories.collectAsState()

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
                toolbarOption = stringResource(R.string.save),
                onItemClick = {
                    navController.navigate(Screen.AddCategory.route)
                }
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                InputAmountTextField(
                    onValueChange = {
                        budgetAmount.value = it
                    },
                    defaultAmount = budgetAmount
                )
                Spacer(modifier = Modifier.height(8.dp))
                RegularTextField(
                    onValueChange = {},
                    placeholder = stringResource(R.string.whats_this_expense_for),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(16.dp))
                CategoriesTitle(
                    isEditMode = isEditable,
                    onEditButtonClick = {
                        isEditable = !isEditable
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.weight(1f)) {
                    CategoriesList(
                        categories,
                        onCategorySelected = {},
                        isEditMode = isEditable,
                        viewModel = viewModel
                    )
                }
            }
        }

        CalendarButton(
            selectedDate = selectedDate,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentWidth()
                .padding(16.dp)
                .border(
                    BorderStroke(width = 1.dp, color = Color.Gray),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { showBottomSheet = true }
        )
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.toEpochDay() * 86400000,
        yearRange = IntRange(2000, 2100)
    )

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            val newSelectedDate = LocalDate.ofEpochDay(it / 86400000)
            selectedDate = newSelectedDate
            showBottomSheet = false
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                headline = null,
                title = null,
                colors = DatePickerDefaults.colors(
                    containerColor = colorResource(R.color.background),
                )
            )
        }
    }
}

@Composable
fun CategoriesTitle(
    isEditMode: Boolean,
    onEditButtonClick: () -> Unit
) {
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
            text = if (isEditMode) stringResource(R.string.done) else stringResource(R.string.edit),
            style = MaterialTheme.typography.titleMedium.copy(
                color = colorResource(R.color.colorPrimary)
            ),
            modifier = Modifier
                .clickable(onClick = onEditButtonClick)
        )
    }
}

@Composable
fun CategoriesList(
    categories: List<Category>,
    onCategorySelected: () -> Unit,
    isEditMode: Boolean,
    viewModel: BudgetItViewModel
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
                    isSelected = if (!isEditMode) category == selectedCategory else false,
                    onCategoryClick = {
                        selectedCategory = category
                        onCategorySelected()
                    },
                    isEditMode = isEditMode,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onCategoryClick: () -> Unit,
    isEditMode: Boolean,
    viewModel: BudgetItViewModel
) {
    val jiggleAnimation = rememberInfiniteTransition(label = "jiggle")
    val jiggleAngle by jiggleAnimation.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "jiggle rotation"
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(70.dp)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onCategoryClick
            )
            .graphicsLayer {
                if (isEditMode)
                    rotationZ = jiggleAngle
            }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(category.color.hex.toComposeColor())
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
            if (isEditMode) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Delete Category",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                            .padding(4.dp)
                            .clickable {
                                viewModel.deleteCategory(category)
                            }
                    )
                }
            }
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
fun CalendarButton(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier
) {
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