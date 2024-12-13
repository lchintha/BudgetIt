package com.iquad.budgetit.model

import com.iquad.budgetit.storage.Category

enum class AppearanceOption {Light, Dark, System}

enum class Currency(val symbol: String) {
    USD("$"),
    EUR("€"),
    GBP("£"),
    JPY("¥"),
    RUP("₹")
}