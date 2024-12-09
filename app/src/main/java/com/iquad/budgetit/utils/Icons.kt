package com.iquad.budgetit.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material.icons.rounded.Checkroom
import androidx.compose.material.icons.rounded.ChildCare
import androidx.compose.material.icons.rounded.CleaningServices
import androidx.compose.material.icons.rounded.CloudDownload
import androidx.compose.material.icons.rounded.Construction
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.DevicesOther
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.EventSeat
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.FamilyRestroom
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Flight
import androidx.compose.material.icons.rounded.HealthAndSafety
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Luggage
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Shower
import androidx.compose.material.icons.rounded.SportsTennis
import androidx.compose.material.icons.rounded.Wifi
import androidx.compose.material.icons.rounded.Yard
import androidx.compose.ui.graphics.vector.ImageVector

enum class CategoryIcon(val imageVector: ImageVector) {
    // Essential Categories
    GROCERY(Icons.Rounded.ShoppingCart),
    RESTAURANTS(Icons.Rounded.Restaurant),
    ENTERTAINMENT(Icons.Rounded.Movie),
    UTILITIES(Icons.Rounded.Settings),
    VACATION(Icons.Rounded.Flight),
    EDUCATION(Icons.Rounded.School),
    HOUSING(Icons.Rounded.Home),
    TRANSPORTATION(Icons.Rounded.DirectionsCar),

    // Personal Categories
    HEALTH(Icons.Rounded.HealthAndSafety),
    FITNESS(Icons.Rounded.FitnessCenter),
    BEAUTY(Icons.Rounded.Face),
    PERSONAL_CARE(Icons.Rounded.Shower),

    // Financial Categories
    INSURANCE(Icons.Rounded.Shield),
    INVESTMENTS(Icons.AutoMirrored.Rounded.TrendingUp),
    TAXES(Icons.Rounded.Calculate),
    DEBT_PAYMENT(Icons.Rounded.CreditCard),

    // Lifestyle Categories
    PETS(Icons.Rounded.Pets),
    HOBBIES(Icons.Rounded.SportsTennis),
    GIFTS(Icons.Rounded.CardGiftcard),
    CHARITIES(Icons.Rounded.CurrencyExchange),

    // Technology and Communication
    ELECTRONICS(Icons.Rounded.DevicesOther),
    PHONE_BILL(Icons.Rounded.PhoneAndroid),
    INTERNET_BILL(Icons.Rounded.Wifi),
    SOFTWARE_SUBSCRIPTIONS(Icons.Rounded.CloudDownload),

    // Family and Children
    CHILDCARE(Icons.Rounded.ChildCare),
    FAMILY_EXPENSES(Icons.Rounded.FamilyRestroom),
    EDUCATION_EXPENSES(Icons.Rounded.School),

    // Home and Maintenance
    HOME_IMPROVEMENT(Icons.Rounded.Construction),
    GARDENING(Icons.Rounded.Yard),
    CLEANING_SUPPLIES(Icons.Rounded.CleaningServices),

    // Miscellaneous
    CLOTHING(Icons.Rounded.Checkroom),
    BOOKS(Icons.Rounded.AutoStories),
    TRAVEL_ACCESSORIES(Icons.Rounded.Luggage),
    EVENTS(Icons.Rounded.EventSeat),

    // Catch-all
    OTHER(Icons.Rounded.MoreHoriz)
}

enum class CategoryColor(val color: String) {
    LIGHT_GRAY("#D3D3D3"),
    SILVER("#C0C0C0"),
    GRAY("#808080"),
    LIGHT_BLUE("#ADD8E6"),
    SKY_BLUE("#87CEFA"),
    CYAN("#008B8B"),
    LIGHT_GREEN("#90EE90"),
    YELLOW("#FFFF00"),
    GOLDENROD("#DAA520"),
    CORNFLOWER_BLUE("#6495ED"),
    CORAL("#FF7F50"),
    PINK("#FFC0CB"),
    LAVENDER_BLUSH("#9400D3"), // Much darker Lavender Blush
    MINT_CREAM("#00FA9A"), // Much darker Mint Cream
    AZURE("#007FFF"),
    PALE_VIOLET_RED("#DB7093"),
    LIGHT_SALMON("#FFA07A"),
    DARK_SEA_GREEN("#8FBC8F"),
    MEDIUM_AQUAMARINE("#66CDAA"),
    MEDIUM_TURQUOISE("#48D1CC")
}