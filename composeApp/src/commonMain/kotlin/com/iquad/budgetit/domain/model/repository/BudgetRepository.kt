interface BudgetRepository {
    /**
     * Persists the given budget to storage
     * @param budget The budget to save
     */
    suspend fun saveBudget(budget: Budget)

    /**
     * Retrieves the currently saved budget
     * @return The saved budget or null if no budget is set
     */
    suspend fun getBudget(): Budget?

    /**
     * Checks if a budget has been set
     * @return true if a budget exists, false otherwise
     */
    suspend fun hasBudgetSet(): Boolean
}