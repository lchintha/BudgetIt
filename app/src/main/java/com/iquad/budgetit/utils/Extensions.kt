package com.iquad.budgetit.utils

import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Extension function to convert hex to Compose Color
fun String.toComposeColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}

// Date conversion extension functions
fun LocalDate.toFormattedString(): String {
    // ISO 8601 format (YYYY-MM-DD)
    // This format makes it easy to extract month and perform comparisons
    return this.format(DateTimeFormatter.ISO_LOCAL_DATE)
}

fun String.toLocalDate(): LocalDate {
    // Convert string back to LocalDate if needed
    return LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
}

fun String.toFormattedDate(
    inputPattern: String = "yyyy-MM-dd",
    outputPattern: String = "MMM d, yyyy"
): String {
    return try {
        val date = LocalDate.parse(this, DateTimeFormatter.ofPattern(inputPattern))
        date.format(DateTimeFormatter.ofPattern(outputPattern))
    } catch (e: Exception) {
        throw IllegalArgumentException("Failed to parse date: $this", e)
    }
}

fun Double.roundToTwoDecimalPlaces(): Double = "%.2f".format(this).toDouble()