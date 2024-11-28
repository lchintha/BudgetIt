package com.iquad.budgetit.model

import com.iquad.budgetit.utils.CategoryIcon

data class Category(
    val name: String,
    val icon: CategoryIcon,
    val color: String
)