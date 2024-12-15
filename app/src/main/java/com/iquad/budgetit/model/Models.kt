package com.iquad.budgetit.model

enum class AppearanceOption {Light, Dark, System}

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