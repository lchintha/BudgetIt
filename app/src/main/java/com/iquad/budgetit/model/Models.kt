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

data class Expense(
    val id: Int,
    val title: String,
    val amount: Double,
    val date: String,
    val category: Category
)