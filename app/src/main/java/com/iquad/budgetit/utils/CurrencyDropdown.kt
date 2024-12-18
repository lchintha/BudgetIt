package com.iquad.budgetit.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iquad.budgetit.model.Currency

@Composable
fun CurrencyDropdown(
    selectedCurrency: MutableState<Currency>,
    size: Int = 16,
    modifier: Modifier = Modifier
) {
    val isDropDownExpanded = remember { mutableStateOf(false) }
    val items = Currency.entries

    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(
                    text = selectedCurrency.value.symbol,
                    style = TextStyle(
                        fontSize = size.sp
                    )
                )
                Icon(
                    imageVector = if (isDropDownExpanded.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    contentDescription = "DropDown Icon",
                    modifier = Modifier.size(
                        size.dp
                    )
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                items.forEachIndexed { _, item ->
                    DropdownMenuItem(text = {
                        Text(text = item.symbol)
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            selectedCurrency.value = item
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CurrencyDropdownPreview() {
    CurrencyDropdown(
        selectedCurrency = remember { mutableStateOf(Currency.USD) }
    )
}