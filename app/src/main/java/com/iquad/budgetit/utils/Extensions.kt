package com.iquad.budgetit.utils

import androidx.compose.ui.graphics.Color

// Extension function to convert hex to Compose Color
fun String.toComposeColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}