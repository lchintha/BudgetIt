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
import androidx.compose.material3.BottomSheetDefaults
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
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
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.storage.Category
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.utils.CategoryColor
import com.iquad.budgetit.utils.CategoryIcon
import com.iquad.budgetit.utils.GlobalStaticMessage
import com.iquad.budgetit.utils.InputAmountTextField
import com.iquad.budgetit.utils.MessageType
import com.iquad.budgetit.utils.RegularTextField
import com.iquad.budgetit.utils.toComposeColor
import com.iquad.budgetit.viewmodel.BudgetItViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    navController: NavController,
    viewModel: BudgetItViewModel,
    expenseId: Int?
) {
    val uiState by viewModel.uiState.observeAsState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var isEditable by remember { mutableStateOf(false) }
    val categories by viewModel.categories.collectAsState()
    val budget by viewModel.budgetState.collectAsState()
    val showDialog by viewModel.displayDialog.collectAsState()
    val deletingCategoryId by viewModel.deletingCategory.collectAsState()
    val currentExpense by viewModel.currentExpense.collectAsState()

    val expenseAmount = remember { mutableStateOf("") }
    val expenseTitle = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf<Category?>(null) }
    val updatingCategory = remember { mutableIntStateOf(0) }
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.value.toEpochDay() * 86400000,
        yearRange = IntRange(2000, 2100)
    )
    LaunchedEffect(selectedDate.value) {
        datePickerState.selectedDateMillis = selectedDate.value.toEpochDay() * 86400000
    }
    LaunchedEffect(key1 = true) {
        viewModel.getCategories()
        expenseId?.let { id ->
            viewModel.getExpenseById(id)
        }
    }
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            val newSelectedDate = LocalDate.ofEpochDay(it / 86400000)
            selectedDate.value = newSelectedDate
            showBottomSheet = false
        }
    }
    LaunchedEffect(currentExpense) {
        if (expenseId == null) return@LaunchedEffect
        currentExpense?.let { expense ->
            expenseAmount.value = expense.data.amount.toString()
            expenseTitle.value = expense.data.title
            selectedCategory.value = expense.category
            selectedDate.value = LocalDate.parse(expense.data.date)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 56.dp)
        ) {
            BudgetItToolBar(
                title = stringResource(if (expenseId == null) R.string.add_expense else R.string.update_expense),
                toolbarOption = stringResource(R.string.save),
                onBackPressed = { navController.popBackStack() },
                onItemClicked = {
                    if (expenseId == null) {
                        viewModel.saveExpense(
                            amount = if (expenseAmount.value.isEmpty()) 0.0 else expenseAmount.value.toDouble(),
                            title = expenseTitle.value,
                            date = selectedDate.value,
                            category = selectedCategory.value
                        )
                    } else {
                        viewModel.updateExpenseById(
                            expenseId = expenseId,
                            amount = if (expenseAmount.value.isEmpty()) 0.0 else expenseAmount.value.toDouble(),
                            title = expenseTitle.value,
                            date = selectedDate.value,
                            category = selectedCategory.value ?: return@BudgetItToolBar
                        )
                    }
                }
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                InputAmountTextField(
                    onValueChange = {
                        expenseAmount.value = it
                    },
                    defaultAmount = expenseAmount,
                    currency = budget?.currency ?: Currency.USD
                )
                Spacer(modifier = Modifier.height(8.dp))
                RegularTextField(
                    onValueChange = { title ->
                        expenseTitle.value = title
                    },
                    placeholder = stringResource(R.string.whats_this_expense_for),
                    prePopulatedText = expenseTitle,
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
                        isEditMode = isEditable,
                        viewModel = viewModel,
                        selectedCategory = selectedCategory,
                        onCustomCategoryItemClick = {
                            navController.navigate(Screen.AddCategory.route)
                        }
                    )
                }
            }
        }

        CalendarButton(
            selectedDate = selectedDate.value,
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
                    containerColor = BottomSheetDefaults.ContainerColor,
                )
            )
        }
    }

    if (showDialog) {
        DeleteCategoryDialog(
            categories = categories,
            deletingCategoryId = deletingCategoryId,
            updatingCategory = updatingCategory,
            onDismiss = {
                viewModel.dismissDialog()
            },
            onUpdateCategory = {
                viewModel.updateCategoryBeforeDeleting(
                    updatingCategory.intValue,
                )
            },
            onDeleteExpenses = {
                viewModel.deleteExpensesAlongWithCategory()
            }
        )
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is BudgetItViewModel.UiState.Error -> {
                GlobalStaticMessage.show(
                    message = "Enter All Fields",
                    messageType = MessageType.FAILURE
                )
                viewModel.resetState()
            }

            BudgetItViewModel.UiState.Success -> {
                navController.popBackStack()
                viewModel.resetState()
            }

            else -> {}
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
                color = MaterialTheme.colorScheme.onSurface
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
    isEditMode: Boolean,
    viewModel: BudgetItViewModel,
    selectedCategory: MutableState<Category?>,
    onCustomCategoryItemClick: () -> Unit = {}
) {
    // Create a custom category with a predefined icon and color
    val customCategory = Category(
        name = "Custom",
        icon = CategoryIcon.ADD,
        color = CategoryColor.LIGHT_GRAY
    )

    // Combine existing categories with the custom category
    val allCategories = if (isEditMode) categories else categories + customCategory

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
            items(allCategories) { category ->
                // Check if it's the custom category
                val isCustomCategory = category.name == "Custom"
                CategoryItem(
                    category = category,
                    isSelected = if (!isEditMode) category == selectedCategory.value else false,
                    onCategoryClick = {
                        if (!isCustomCategory) {
                            selectedCategory.value = category
                        } else {
                            onCustomCategoryItemClick.invoke()
                        }
                    },
                    isEditMode = isEditMode,
                    viewModel = viewModel,
                    isCustomCategory = isCustomCategory
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
    viewModel: BudgetItViewModel,
    isCustomCategory: Boolean
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
                    .background(
                        if (isCustomCategory) Color.Transparent
                        else category.color.hex.toComposeColor()
                    )
                    .border(
                        width = if (isCustomCategory) 2.dp else 3.dp,
                        color = if (isCustomCategory) Color.LightGray
                        else if (isSelected) colorResource(R.color.colorPrimary)
                        else Color.Transparent,
                        shape = CircleShape
                    )
                    .graphicsLayer {
                        if (isSelected && !isCustomCategory) {
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
                    tint = if (isCustomCategory) Color.Gray else Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }
            if (isEditMode && !isCustomCategory) {
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
                                viewModel.deleteCategory(
                                    categoryId = category.id
                                )
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
            modifier = Modifier.fillMaxWidth(),
            color = if (isCustomCategory) Color.Gray else Color.Unspecified
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