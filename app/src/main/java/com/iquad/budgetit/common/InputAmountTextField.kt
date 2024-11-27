package com.iquad.budgetit.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun InputAmountTextField(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var amount by remember { mutableStateOf("") }

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = amount,
        onValueChange = { input ->
            if (input.matches(Regex("^\\d*\\.?\\d{0,2}\$"))) {
                amount = input
                onValueChange(input)
            }
        },
        placeholder = {
            Text(
                text = "$0",
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                fontSize = 36.sp,
                textAlign = TextAlign.Center
            )
        },
        textStyle = TextStyle(
            fontSize = 36.sp,
            textAlign = TextAlign.Center,

        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
//        visualTransformation = DollarVisualTransformation(),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Transparent
        ),
    )
}

@Preview
@Composable
fun InputAmountTextFieldPreview() {
    InputAmountTextField(
        onValueChange = {}
    )
}