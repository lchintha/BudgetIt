/**
 * This use case handles checking whether a budget has been set. It's used primarily
 * for determining if the welcome screen should be shown or skipped.
 */
class GetBudgetStatusUseCase(private val repository: BudgetRepository) {
    /**
     * Checks if a budget has been set
     * @return true if a budget exists, false otherwise
     */
    suspend operator fun invoke(): Boolean = repository.hasBudgetSet()
}