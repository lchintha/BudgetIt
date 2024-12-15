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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iquad.budgetit.R
import com.iquad.budgetit.model.TabItem
import com.iquad.budgetit.model.TimeFrame
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.viewmodel.BudgetItViewModel

@Composable
fun SpendingAnalysisScreen(
    navController: NavController,
    viewModel: BudgetItViewModel
) {

    val selectedTab by viewModel.selectedTab.collectAsState()
    val selectedTimeFrame by viewModel.selectedTimeFrame.collectAsState()

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
                    selectedTab,
                    onClick = {
                        viewModel.setSelectedTab(it)
                        viewModel.setOrUpdateTimeFrame()
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TimeFrameTabBar(
                    selectedTimeFrame = selectedTimeFrame,
                    onChangeTimeFrameClick = {
                        viewModel.setOrUpdateTimeFrame(
                            isInitialSetup = false,
                            toPrevious = it
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun IntervalTabBar(
    selectedTab: TabItem,
    onClick: (item: TabItem) -> Unit
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
            isSelected = selectedTab == TabItem.Weekly,
            onClick = {
                onClick(TabItem.Weekly)
            }
        )
        TabItem(
            title = "Monthly",
            isSelected = selectedTab == TabItem.Monthly,
            onClick = {
                onClick(TabItem.Monthly)
            }
        )
        TabItem(
            title = "Yearly",
            isSelected = selectedTab == TabItem.Yearly,
            onClick = {
                onClick(TabItem.Yearly)
            }
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
    selectedTimeFrame: TimeFrame,
    onChangeTimeFrameClick: (Boolean) -> Unit
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
                tint = Color.Gray,
                modifier = Modifier.clickable {
                    onChangeTimeFrameClick(true)
                }
            )
            Text(
                text = selectedTimeFrame.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next",
                tint = Color.Gray,
                modifier = Modifier.clickable {
                    onChangeTimeFrameClick(false)
                }
            )
        }
    }
}