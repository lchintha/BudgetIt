package com.iquad.budgetit.storage.defaultstorage

import com.iquad.budgetit.storage.BudgetItRepository
import com.iquad.budgetit.storage.Category
import com.iquad.budgetit.utils.CategoryColor
import com.iquad.budgetit.utils.CategoryIcon

object CategoryInitializer {
    suspend fun initializeCategories(repository: BudgetItRepository) {
        if (repository.getCategoryCount() == 0) {
            val predefinedCategories = listOf(
                Category(
                    name = "Grocery",
                    icon = CategoryIcon.GROCERY,
                    color = CategoryColor.PINK
                ),
                Category(
                    name = "Restaurants",
                    icon = CategoryIcon.RESTAURANTS,
                    color = CategoryColor.CORAL
                ),
                Category(
                    name = "Entertainment",
                    icon = CategoryIcon.ENTERTAINMENT,
                    color = CategoryColor.LAVENDER_BLUSH
                ),
                Category(
                    name = "Utilities",
                    icon = CategoryIcon.UTILITIES,
                    color = CategoryColor.MINT_CREAM
                ),
                Category(
                    name = "Vacation",
                    icon = CategoryIcon.VACATION,
                    color = CategoryColor.AZURE
                ),
                Category(
                    name = "Education",
                    icon = CategoryIcon.EDUCATION,
                    color = CategoryColor.PALE_VIOLET_RED
                ),
                Category(
                    name = "Housing",
                    icon = CategoryIcon.HOUSING,
                    color = CategoryColor.LIGHT_SALMON
                ),
                Category(
                    name = "Transportation",
                    icon = CategoryIcon.TRANSPORTATION,
                    color = CategoryColor.DARK_SEA_GREEN
                ),
                Category(
                    name = "Health",
                    icon = CategoryIcon.HEALTH,
                    color = CategoryColor.MEDIUM_AQUAMARINE
                ),
                Category(
                    name = "Fitness",
                    icon = CategoryIcon.FITNESS,
                    color = CategoryColor.MEDIUM_TURQUOISE
                ),
            )
            repository.insertCategories(predefinedCategories)
        }
    }
}