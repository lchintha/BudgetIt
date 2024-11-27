package com.iquad.budgetit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.iquad.budgetit.common.BudgetItToolBar
import com.iquad.budgetit.common.InputAmountTextField

@Composable
fun AddExpenseScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BudgetItToolBar(
            navController = navController,
            title = stringResource(R.string.add_expense),
            toolbarOption = stringResource(R.string.save)
        )
        Spacer(modifier = Modifier.height(8.dp))
        InputAmountTextField(
            onValueChange = {}
        )
    }
}

@Preview
@Composable
fun AddExpenseScreenPreview(){
    AddExpenseScreen(rememberNavController())
}