package com.iquad.budgetit.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val LightColors = lightColors(
    primary = Blue,
    primaryVariant = DarkBlue,
    secondary = Orange
    // Add other color customizations
)

private val DarkColors = darkColors(
    primary = LightBlue,
    primaryVariant = Blue,
    secondary = LightOrange
    // Add other color customizations
)

@Composable
fun BudgetItTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}