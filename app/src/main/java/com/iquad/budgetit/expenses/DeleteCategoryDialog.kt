package com.iquad.budgetit.expenses

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.iquad.budgetit.R
import com.iquad.budgetit.storage.Category
import com.iquad.budgetit.utils.toComposeColor

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun DeleteCategoryDialog(
    categories: List<Category>,
    deletingCategoryId: Int,
    updatingCategory: MutableState<Int>,
    onDismiss: () -> Unit,
    onUpdateCategory: () -> Unit,
    onDeleteExpenses: () -> Unit
) {
    val isConfirmButtonVisible = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf<SelectedOption?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black.copy(alpha = 0.5f)
            )
    ) {
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            modifier = Modifier.clickable { onDismiss() }
                        )
                    }
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.delete_category),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(R.string.delete_category_confirmation),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.update_category),
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        width = 2.dp,
                                        color = if (selectedOption.value == SelectedOption.Update) colorResource(
                                            R.color.colorPrimary
                                        ) else Color.Gray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(vertical = 8.dp, horizontal = 16.dp)
                                    .clickable(
                                        interactionSource = null,
                                        indication = null
                                    ) {
                                        selectedOption.value = SelectedOption.Update
                                    }
                                    .graphicsLayer {
                                        if (selectedOption.value == SelectedOption.Update) {
                                            scaleX = 1.1f
                                            scaleY = 1.1f
                                        } else {
                                            scaleX = 1f
                                            scaleY = 1f
                                        }
                                    },
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 12.sp
                                ),
                                textAlign = TextAlign.Center,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(R.string.delete_expenses),
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        width = 2.dp,
                                        color = if (selectedOption.value == SelectedOption.Delete) colorResource(
                                            R.color.colorPrimary
                                        ) else Color.Gray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(vertical = 8.dp, horizontal = 16.dp)
                                    .clickable(
                                        interactionSource = null,
                                        indication = null
                                    ) {
                                        isConfirmButtonVisible.value = true
                                        selectedOption.value = SelectedOption.Delete
                                    }
                                    .graphicsLayer {
                                        if (selectedOption.value == SelectedOption.Delete) {
                                            scaleX = 1.1f
                                            scaleY = 1.1f
                                        } else {
                                            scaleX = 1f
                                            scaleY = 1f
                                        }
                                    },
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 12.sp
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                        if (selectedOption.value == SelectedOption.Update) {
                            Text(
                                text = stringResource(R.string.select_category),
                                style = MaterialTheme.typography.titleMedium
                            )
                            CategoriesList(
                                categories = categories.filter { it.id != deletingCategoryId },
                                updatingCategory = updatingCategory,
                                isConfirmButtonVisible = isConfirmButtonVisible
                            )
                        }

                        if (isConfirmButtonVisible.value) {
                            Button(
                                onClick = {
                                    when (selectedOption.value) {
                                        SelectedOption.Update -> onUpdateCategory()
                                        SelectedOption.Delete -> onDeleteExpenses()
                                        null -> {}
                                    }
                                },
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                                    .height(36.dp)
                                    .align(Alignment.End),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.colorPrimary)
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.confirm),
                                    color = Color.White,
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun CategoriesList(
    categories: List<Category>,
    updatingCategory: MutableState<Int>,
    isConfirmButtonVisible: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 60.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    isSelected = category.id == updatingCategory.value,
                    onCategoryClick = {
                        updatingCategory.value = category.id
                        isConfirmButtonVisible.value = true
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
    onCategoryClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(45.dp)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onCategoryClick
            )
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        category.color.hex.toComposeColor()
                    )
                    .border(
                        width = 3.dp,
                        color = if (isSelected) colorResource(R.color.colorPrimary)
                        else Color.Transparent,
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
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.name,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = Color.Unspecified
        )
    }
}

sealed class SelectedOption {
    data object Update : SelectedOption()
    data object Delete : SelectedOption()
}