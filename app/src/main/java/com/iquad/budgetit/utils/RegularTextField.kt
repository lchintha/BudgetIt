package com.iquad.budgetit.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegularTextField(
    onValueChange: (String) -> Unit,
    placeholder: String,
    prePopulatedText: String = "",
    modifier: Modifier
) {
    var value by remember { mutableStateOf(prePopulatedText) }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 0.5.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ),
        value = value,
        onValueChange = { input ->
            value = input
            onValueChange(input)
        },
        placeholder = {
            Text(
                text = placeholder,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
    )
}

@Preview
@Composable
fun RegularTextFieldPreview() {
    RegularTextField(
        onValueChange = {},
        placeholder = "Whats this expense for?",
        modifier = Modifier
    )
}