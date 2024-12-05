package com.iquad.budgetit.homescreen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.iquad.budgetit.R
import com.iquad.budgetit.Screen
import com.iquad.budgetit.charts.HalfCircleProgressBar

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        HomeScreenToolBar(navController)
        Spacer(modifier = Modifier.height(8.dp))
        HalfCircleProgressBar(
            spentAmount = 4506.43f,
            totalAmount = 8000f
        )
        AddExpenseButton(navController)
        Spacer(modifier = Modifier.height(8.dp))
        RecentExpenses()
    }
}

@Composable
fun HomeScreenToolBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.budget_tracker),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        IconButton(onClick = {
            navController.navigate(Screen.SettingsScreen.route)
        }) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings"
            )
        }
    }
}

@Composable
fun AddExpenseButton(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .background(
                color = colorResource(R.color.light_blue)
            )
            .clickable {
                navController.navigate(Screen.AddExpenseScreen.route)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    )
                    .background(
                        color = colorResource(R.color.light_blue)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    modifier = Modifier
                        .padding(10.dp),
                    contentDescription = "Add Button"
                )
            }
            Text(
                text = stringResource(R.string.add_expense),
                modifier = Modifier
                    .padding(start = 18.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Composable
fun RecentExpenses() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.recent_expenses),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.all),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = colorResource(R.color.colorPrimary)
                )
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}