package com.iquad.budgetit.presentation.welcome.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iquad.budgetit.domain.model.Currency

@Composable
fun CurrencySelector(
    selectedCurrency: Currency,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onCurrencySelected: (Currency) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Currency selector button
        Surface(
            modifier = Modifier
                .clickable { onExpandedChange(true) }
                .height(56.dp),
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colors.surface,
            elevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedCurrency.symbol,
                    style = MaterialTheme.typography.h5
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = selectedCurrency.code,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select currency",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Dropdown menu
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.width(200.dp)
        ) {
            Currency.SUPPORTED_CURRENCIES.forEach { currency ->
                DropdownMenuItem(
                    onClick = {
                        onCurrencySelected(currency)
                        onExpandedChange(false)
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(currency.symbol)
                        Text(
                            text = currency.code,
                            modifier = Modifier.width(48.dp)
                        )
                        Text(
                            text = currency.name,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}