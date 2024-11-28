package com.iquad.budgetit.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class CategoryIcon(val imageVector: ImageVector) {
    ADD(Icons.Rounded.Add),
    HOME(Icons.Rounded.Home),
    SETTINGS(Icons.Rounded.Settings),
    PERSON(Icons.Rounded.Person),
    SHOPPING(Icons.Rounded.ShoppingCart),
}