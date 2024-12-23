package com.iquad.budgetit.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iquad.budgetit.R
import com.iquad.budgetit.settings.TitleText
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.utils.CategoryColor
import com.iquad.budgetit.utils.CategoryIcon
import com.iquad.budgetit.utils.GlobalStaticMessage
import com.iquad.budgetit.utils.MessageType
import com.iquad.budgetit.utils.RegularTextField
import com.iquad.budgetit.utils.toComposeColor
import com.iquad.budgetit.viewmodel.BudgetItViewModel

@Composable
fun AddCategory(
    navController: NavController,
    viewModel: BudgetItViewModel
) {
    val categoryName = remember { mutableStateOf("") }
    val selectedColor = remember { mutableStateOf<CategoryColor?>(null) }
    val selectedIcon = remember { mutableStateOf<CategoryIcon?>(null) }
    val uiState by viewModel.uiState.observeAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            BudgetItToolBar(
                title = stringResource(R.string.add_category),
                toolbarOption = stringResource(R.string.save),
                onBackPress =  { navController.popBackStack() },
                onItemClick = {
                    viewModel.addCategory(
                        categoryName.value,
                        selectedColor.value,
                        selectedIcon.value
                    )
                }
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                CategoryNameSection(categoryName)
                Spacer(modifier = Modifier.height(16.dp))
                ColorsList(selectedColor)
                Spacer(modifier = Modifier.height(16.dp))
                IconsList(selectedIcon)
            }
        }
    }

    LaunchedEffect(uiState) {
        when(uiState) {
            is BudgetItViewModel.UiState.Error -> {
                GlobalStaticMessage.show(
                    message = "Select All Fields",
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
fun CategoryNameSection(
    categoryName: MutableState<String>
) {
    TitleText(
        title = stringResource(R.string.category_name),
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    RegularTextField(
        onValueChange = {
            categoryName.value = it
        },
        placeholder = stringResource(R.string.category_name),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun ColorsList(
    selectedColor: MutableState<CategoryColor?>
) {
    TitleText(
        title = stringResource(R.string.color),
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 48.dp),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(CategoryColor.entries.size) { index ->
            ColorItem(
                color = CategoryColor.entries[index],
                isSelected = CategoryColor.entries[index] == selectedColor.value,
                onColorSelected = {
                    selectedColor.value = CategoryColor.entries[index]
                }
            )
        }
    }
}

@Composable
fun IconsList(
    selectedIcon: MutableState<CategoryIcon?>
) {
    TitleText(
        title = stringResource(R.string.icon),
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 48.dp),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(CategoryIcon.entries.size) { index ->
            IconItem(
                icon = CategoryIcon.entries[index],
                isSelected = CategoryIcon.entries[index] == selectedIcon.value,
                onIconSelected = {
                    selectedIcon.value = CategoryIcon.entries[index]
                }
            )
        }
    }
}


@Composable
fun ColorItem(
    color: CategoryColor,
    isSelected: Boolean,
    onColorSelected: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .size(48.dp)
            .clip(CircleShape)
            .background(color.hex.toComposeColor())
            .border(
                width = 3.dp,
                color = if (isSelected) colorResource(R.color.colorPrimary) else Color.Transparent,
                shape = CircleShape
            )
            .clickable(
                interactionSource = null,
                indication = null
            ) {
                onColorSelected.invoke()
            }
    )
}

@Composable
fun IconItem(
    icon: CategoryIcon,
    isSelected: Boolean,
    onIconSelected: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .size(48.dp)
            .clip(CircleShape)
            .background(colorResource(R.color.light_blue))
            .border(
                width = 3.dp,
                color = if (isSelected) colorResource(R.color.colorPrimary) else Color.Transparent,
                shape = CircleShape
            )
            .clickable(
                interactionSource = null,
                indication = null
            ) {
                onIconSelected.invoke()
            }
    ) {
        Icon(
            imageVector = icon.imageVector,
            contentDescription = icon.name,
            tint = Color.Black,
            modifier = Modifier.size(30.dp)
        )
    }
}