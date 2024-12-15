package com.iquad.budgetit.expenses

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iquad.budgetit.R
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.viewmodel.BudgetItViewModel

@Composable
fun SpendingAnalysisScreen(
    navController: NavController,
    viewModel: BudgetItViewModel
) {
    val selectedTab = remember { mutableStateOf<TabItem>(TabItem.Monthly) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Column {
                BudgetItToolBar(
                    navController = navController,
                    title = stringResource(R.string.spending_analysis),
                )
            }
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                IntervalTabBar(
                    selectedTab
                )
                Spacer(modifier = Modifier.height(8.dp))
                TimeFrameTabBar("Dev 2024")
            }
        }
    }
}

@Composable
fun IntervalTabBar(
    selectedTab: MutableState<TabItem>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.light_blue),
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TabItem(
            title = "Weekly",
            isSelected = selectedTab.value == TabItem.Weekly,
            onClick = {
                selectedTab.value = TabItem.Weekly
            }
        )
        TabItem(
            title = "Monthly",
            isSelected = selectedTab.value == TabItem.Monthly,
            onClick = { selectedTab.value = TabItem.Monthly }
        )
        TabItem(
            title = "Yearly",
            isSelected = selectedTab.value == TabItem.Yearly,
            onClick = { selectedTab.value = TabItem.Yearly }
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
    title: String
) {
    Box(
        modifier = Modifier
            .background(
                color = colorResource(R.color.light_blue),
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
                tint = Color.Gray
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next",
                tint = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun TimeFrameTabBarPreview() {
    TimeFrameTabBar(
        "sdjknf"
    )
}

sealed class TabItem {
    data object Weekly : TabItem()
    data object Monthly : TabItem()
    data object Yearly : TabItem()
}