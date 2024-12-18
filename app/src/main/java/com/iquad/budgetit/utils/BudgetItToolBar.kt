package com.iquad.budgetit.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iquad.budgetit.R

@Composable
fun BudgetItToolBar(
    title: String,
    toolbarOption: String? = null,
    onBackPress: () -> Unit = {},
    onItemClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onBackPress.invoke()
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        toolbarOption?.let { toolbarTitle ->
            Text(
                text = toolbarTitle,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = colorResource(R.color.colorPrimary)
                ),
                modifier = Modifier.padding(end = 10.dp)
                    .clickable {
                        onItemClick.invoke()
                    }
            )
        }

    }
}

@Preview
@Composable
fun BudgetItToolBarPreview() {
    BudgetItToolBar(
        "Add Expense",
        "Save",
        onBackPress = {},
    )
}