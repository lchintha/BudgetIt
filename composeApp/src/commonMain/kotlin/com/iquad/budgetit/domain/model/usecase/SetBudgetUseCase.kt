/**
 * This use case handles the business logic for setting a new budget. It encapsulates
 * the conversion from raw amount to Budget domain model and delegates storage to
 * the repository.
 */
class SetBudgetUseCase(private val repository: BudgetRepository) {
    /**
     * Sets a new budget with the given amount
     * @param amount The budget amount to set
     */
    suspend operator fun invoke(amount: Double) {
        repository.saveBudget(Budget(amount))
    }
}