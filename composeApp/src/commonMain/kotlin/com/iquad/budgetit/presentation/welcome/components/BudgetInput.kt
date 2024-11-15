package com.iquad.budgetit.presentation.welcome.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * A reusable composable component for budget input. It handles the currency symbol
 * and text input formatting for budget entries.
 */
@Composable
fun BudgetInput(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Currency selector
        CurrencySelector(
            selectedCurrency = selectedCurrency,
            isExpanded = isDropdownExpanded,
            onExpandedChange = onDropdownExpandedChange,
            onCurrencySelected = onCurrencySelected,
            modifier = Modifier.width(120.dp)
        )

        Spacer(Modifier.width(12.dp))

        // Budget amount input
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.h4.copy(
                textAlign = TextAlign.Center,
                color = if (isError) {
                    MaterialTheme.colors.error
                } else {
                    MaterialTheme.colors.onSurface
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier
                .width(120.dp)
                .testTag("budget_input_field") // For UI testing
        )
    }

    // Error message if needed
    if (isError) {
        Text(
            text = "Please enter a valid amount",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}