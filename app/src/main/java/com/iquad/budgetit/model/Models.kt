package com.iquad.budgetit.model

import com.iquad.budgetit.storage.Category

enum class ThemeMode {Light, Dark, System}

enum class Currency(val symbol: String) {
    USD("$"),
    EUR("€"),
    GBP("£"),
    JPY("¥"),
    RUP("₹")
}

data class TimeFrame(
    val title: String,
    val startDate: String,
    val endDate: String
)

sealed class TabItem {
    data object Weekly : TabItem()
    data object Monthly : TabItem()
    data object Yearly : TabItem()
}

data class ExpenseBreakDown(
    val category: Category,
    val amount: Double,
    val percentage: Float
)