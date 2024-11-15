/**
 * This class implements the BudgetRepository interface from the domain layer.
 * It handles the actual data operations using the Storage interface for persistence.
 */
class BudgetRepositoryImpl(
    private val storage: Storage
) : BudgetRepository {

    override suspend fun saveBudget(budget: Budget) {
        storage.putDouble(KEY_BUDGET_AMOUNT, budget.amount)
    }

    override suspend fun getBudget(): Budget? {
        val amount = storage.getDouble(KEY_BUDGET_AMOUNT, -1.0)
        return if (amount != -1.0) Budget(amount) else null
    }

    override suspend fun hasBudgetSet(): Boolean {
        return getBudget() != null
    }

    companion object {
        /**
         * Key used to store the budget amount in the Storage
         */
        private const val KEY_BUDGET_AMOUNT = "budget_amount"
    }
}