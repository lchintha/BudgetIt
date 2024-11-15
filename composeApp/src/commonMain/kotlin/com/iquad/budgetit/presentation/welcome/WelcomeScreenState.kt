/**
 * This data class represents the UI state for the welcome screen.
 * Following the unidirectional data flow pattern, all UI state is centralized here.
 */
data class WelcomeScreenState(
    /**
     * The current budget text input value
     */
    val budgetText: String = "0",

    /**
     * Selected currency symbol
     */
    val selectedCurrency: Currency = Currency.DEFAULT,

    /**
     * Is the currency symbols dropdown expanded
     */
    val isDropdownExpanded: Boolean = false,

    /**
     * Flag to show error state in the UI
     */
    val showError: Boolean = false,

    /**
     * Flag to trigger navigation to home screen
     */
    val shouldNavigateToHome: Boolean = false
)