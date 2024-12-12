package com.iquad.budgetit.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputAmountTextField(
    onValueChange: (String) -> Unit,
    defaultAmount: MutableState<String>,
    includeCurrencySymbol: Boolean = true,
    displayHint: Boolean = true,
    hint: String = "Enter Amount",
    size: Int = 36,
    align: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .padding(0.dp)
    ) {
        TextField(
            modifier = modifier.fillMaxWidth()
                .padding(0.dp),
            value = defaultAmount.value,
            onValueChange = { input ->
                val cleanInput = if (includeCurrencySymbol) input.removePrefix("$") else input
                if (cleanInput.matches(Regex("^\\d*\\.?\\d{0,2}\$"))) {
                    defaultAmount.value = cleanInput
                    onValueChange(cleanInput)
                }
            },
            placeholder = {
                Text(
                    text = if (includeCurrencySymbol) "$0" else "0",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    fontSize = size.sp,
                    textAlign = align
                )
            },
            textStyle = TextStyle(
                fontSize = size.sp,
                textAlign = align,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            visualTransformation = CurrencyVisualTransformation(
                includeCurrencySymbol = includeCurrencySymbol
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
        if (displayHint) {
            Text(
                text = hint,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray,
                textAlign = align,
                fontSize = 12.sp
            )
        }
    }
}

class CurrencyVisualTransformation(
    private val includeCurrencySymbol: Boolean = false
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = if (text.isNotEmpty()) {
            val updatedText = if (includeCurrencySymbol) "$$text" else "$text"
            AnnotatedString(updatedText)
        } else {
            text
        }
        return TransformedText(
            transformedText,
            CurrencyOffsetMapping(
                transformedText,
                includeCurrencySymbol
            )
        )
    }
}

class CurrencyOffsetMapping(
    private val originalText: AnnotatedString,
    private val includeCurrencySymbol: Boolean
) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return when {
            originalText.isEmpty() -> 0
            !includeCurrencySymbol -> offset
            else -> offset + 1
        }
    }

    override fun transformedToOriginal(offset: Int): Int {
        return when {
            originalText.isEmpty() -> 0
            !includeCurrencySymbol -> offset
            offset > 0 -> offset - 1
            else -> 0
        }
    }
}

@Preview
@Composable
fun InputAmountTextFieldPreview() {
    InputAmountTextField(
        onValueChange = {},
        includeCurrencySymbol = false,
        defaultAmount = remember { mutableStateOf("O") }
    )
}