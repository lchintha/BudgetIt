/**
 * Data class representing a currency with its symbol and code
 */
data class Currency(
    val code: String,
    val symbol: String,
    val name: String
) {
    companion object {
        val DEFAULT = Currency("USD", "$", "US Dollar")

        // Common currencies list
        val SUPPORTED_CURRENCIES = listOf(
            Currency("USD", "$", "US Dollar"),
            Currency("EUR", "€", "Euro"),
            Currency("GBP", "£", "British Pound"),
            Currency("JPY", "¥", "Japanese Yen"),
            Currency("INR", "₹", "Indian Rupee"),
            Currency("CNY", "¥", "Chinese Yuan"),
            Currency("AUD", "$", "Australian Dollar"),
            Currency("CAD", "$", "Canadian Dollar"),
            Currency("CHF", "Fr", "Swiss Franc"),
            Currency("NZD", "$", "New Zealand Dollar"),
            Currency("ZAR", "R", "South African Rand"),
            // Add more currencies as needed
        )
    }
}