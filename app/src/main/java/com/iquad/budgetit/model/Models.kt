package com.iquad.budgetit.model

import com.iquad.budgetit.utils.CategoryIcon

data class Category(
    val id: Int,
    val name: String,
    val icon: CategoryIcon,
    val color: String
)

enum class AppearanceOption {Light, Dark, System}

enum class Currency(val symbol: String) {
    USD("$"),
    EUR("€"),
    GBP("£"),
    JPY("¥"),
    RUP("₹")
}