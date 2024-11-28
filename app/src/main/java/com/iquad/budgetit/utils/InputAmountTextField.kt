package com.iquad.budgetit.utils

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun InputAmountTextField(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var amount by remember { mutableStateOf("") }

    Column {
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = amount,
            onValueChange = { input ->
                val cleanInput = input.removePrefix("$")
                if (cleanInput.matches(Regex("^\\d*\\.?\\d{0,2}\$"))) {
                    amount = cleanInput
                    onValueChange(cleanInput)
                }
            },
            placeholder = {
                Text(
                    text = "$0",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            },
            textStyle = TextStyle(
                fontSize = 24.sp,
                textAlign = TextAlign.Center,

                ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            visualTransformation = CurrencyVisualTransformation(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Transparent
            ),
        )
        Text(
            text = "Enter Amount",
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}

class CurrencyVisualTransformation : VisualTransformation{
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = if (text.isNotEmpty()) {
            AnnotatedString("$$text")
        } else {
            text
        }
        return TransformedText(transformedText, CurrencyOffsetMapping(transformedText))
    }
}

class CurrencyOffsetMapping(transformedText: AnnotatedString): OffsetMapping{
    private val text = transformedText.text
    override fun originalToTransformed(offset: Int): Int {
        if(text.isEmpty()) return 0
        return text.length-1
    }

    override fun transformedToOriginal(offset: Int): Int {
        if(text.isEmpty()) return 0
        return text.length-1
    }
}

@Preview
@Composable
fun InputAmountTextFieldPreview() {
    InputAmountTextField(
        onValueChange = {}
    )
}