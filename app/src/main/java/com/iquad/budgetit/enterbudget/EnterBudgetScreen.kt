package com.iquad.budgetit.enterbudget

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.iquad.budgetit.R
import com.iquad.budgetit.Screen

@Composable
fun EnterBudgetScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 60.dp)
            ) {
                Text(
                    text = stringResource(R.string.welcome_to_budget_it),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(R.string.enter_budget_description),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(48.dp))

                // Budget Input Section
                BudgetInputField()
            }
        }

        // Continue Button
        Button(
            onClick = {
                navController.navigate(Screen.HomeScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 36.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(
                text = stringResource(R.string.btn_continue),
                color = Color.White,
            )
        }
    }
}

@Composable
fun BudgetInputField() {
    var budgetInput by remember { mutableStateOf("") }

    val textMeasurer = rememberTextMeasurer()
    val textToMeasure = budgetInput.ifEmpty { "0" }
    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(textToMeasure),
        style = TextStyle(
            fontSize = 48.sp,
            textAlign = TextAlign.Center
        )
    )
    val textWidth = textLayoutResult.size.width

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = Color(0xFFF0F0F0),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$",
                    color = Color.Blue,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            TextField(
                value = budgetInput,
                onValueChange = { input ->
                    budgetInput = input
                },
                modifier = Modifier
                    .width(textWidth.dp)  // Use the calculated width
                    .background(Color.Transparent),
                textStyle = TextStyle(
                    fontSize = 48.sp,
                    textAlign = TextAlign.Start
                ),
                placeholder = {
                    Text(
                        text = "0",
                        color = Color.Black,
                        fontSize = 48.sp,
                        textAlign = TextAlign.Start
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Transparent
                )
            )
        }
        Text(
            text = stringResource(R.string.enter_budget),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun EnterBudgetScreenPreview() {
//    WrapTextFields()
    EnterBudgetScreen(navController = rememberNavController())
}
