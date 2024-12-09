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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.iquad.budgetit.R
import com.iquad.budgetit.settings.TitleText
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.utils.CategoryColor
import com.iquad.budgetit.utils.CategoryIcon
import com.iquad.budgetit.utils.RegularTextField
import com.iquad.budgetit.utils.toComposeColor

@Composable
fun AddCategory(
    navController: NavController
) {
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
                title = stringResource(R.string.add_category),
                toolbarOption = stringResource(R.string.save)
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                CategoryNameSection()
                Spacer(modifier = Modifier.height(16.dp))
                ColorsList()
                Spacer(modifier = Modifier.height(16.dp))
                IconsList()
            }
        }
    }
}

@Composable
fun CategoryNameSection() {
    TitleText(
        title = stringResource(R.string.category_name),
        style = MaterialTheme.typography.titleMedium.copy(
            color = Color.Black
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    RegularTextField(
        onValueChange = {},
        placeholder = stringResource(R.string.category_name),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun ColorsList() {
    var selectedColor by remember { mutableStateOf<CategoryColor?>(null) }
    TitleText(
        title = stringResource(R.string.color),
        style = MaterialTheme.typography.titleMedium.copy(
            color = Color.Black
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
                isSelected = CategoryColor.entries[index] == selectedColor,
                onColorSelected = {
                    selectedColor = CategoryColor.entries[index]
                    println("Selected Color: ${selectedColor?.name}")
                }
            )
        }
    }
}

@Composable
fun IconsList() {
    var selectedIcon by remember { mutableStateOf<CategoryIcon?>(null) }
    TitleText(
        title = stringResource(R.string.icon),
        style = MaterialTheme.typography.titleMedium.copy(
            color = Color.Black
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
                isSelected = CategoryIcon.entries[index] == selectedIcon,
                onIconSelected = {
                    selectedIcon = CategoryIcon.entries[index]
                    println("Selected Icon: ${CategoryIcon.entries[index].name}")
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
            .background(color.color.toComposeColor())
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

@Preview
@Composable
fun AddCategoryPreview() {
    AddCategory(navController = rememberNavController())
}