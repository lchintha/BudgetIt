package com.iquad.budgetit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.model.ExpenseBreakDown
import com.iquad.budgetit.model.TabItem
import com.iquad.budgetit.model.ThemeMode
import com.iquad.budgetit.model.TimeFrame
import com.iquad.budgetit.storage.BudgetEntity
import com.iquad.budgetit.storage.BudgetItRepository
import com.iquad.budgetit.storage.Category
import com.iquad.budgetit.storage.Expense
import com.iquad.budgetit.storage.ExpenseWithCategoryId
import com.iquad.budgetit.storage.PreferencesManager
import com.iquad.budgetit.storage.defaultstorage.CategoryInitializer
import com.iquad.budgetit.utils.CategoryColor
import com.iquad.budgetit.utils.CategoryIcon
import com.iquad.budgetit.utils.roundToTwoDecimalPlaces
import com.iquad.budgetit.utils.toFormattedString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

class BudgetItViewModel(
    private val repository: BudgetItRepository,
    application: Application
) : AndroidViewModel(application = application) {

    private val storageManager = PreferencesManager.getInstance(getApplication())
    private val currentDate: LocalDate = LocalDate.now()
    private val month: String =
        currentDate.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())
    private val year = currentDate.year

    private val _uiState = MutableLiveData<UiState>(UiState.Idle)
    val uiState: LiveData<UiState> get() = _uiState

    private val _budgetState = MutableStateFlow<BudgetEntity?>(null)
    val budgetState: StateFlow<BudgetEntity?> get() = _budgetState

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> get() = _expenses

    private val _totalExpenses = MutableStateFlow(0.0)
    val totalExpenses: StateFlow<Double> get() = _totalExpenses

    private val _displayDialog = MutableStateFlow(false)
    val displayDialog: StateFlow<Boolean> get() = _displayDialog

    private val _deletingCategoryId = MutableStateFlow(-1)
    val deletingCategory: StateFlow<Int> get() = _deletingCategoryId

    private val _selectedTab = MutableStateFlow<TabItem>(TabItem.Monthly)
    val selectedTab: StateFlow<TabItem> get() = _selectedTab

    private val _expensesByTimeFrame = MutableStateFlow<List<Expense>>(emptyList())
    val expensesByTimeFrame: StateFlow<List<Expense>> get() = _expensesByTimeFrame

    private val _expensesByCategoryInTimeFrame = MutableStateFlow<List<Expense>>(emptyList())
    val expensesByCategoryInTimeFrame: StateFlow<List<Expense>> get() = _expensesByCategoryInTimeFrame

    private val _selectedTimeFrame = MutableStateFlow(
        TimeFrame(
            title = "$month $year",
            startDate = currentDate.with(TemporalAdjusters.firstDayOfMonth()).toString(),
            endDate = currentDate.with(TemporalAdjusters.lastDayOfMonth()).toString()
        )
    )
    val selectedTimeFrame: StateFlow<TimeFrame> get() = _selectedTimeFrame

    private val _expensesByCategory = MutableStateFlow<List<ExpenseBreakDown>>(emptyList())
    val expensesByCategory: StateFlow<List<ExpenseBreakDown>> get() = _expensesByCategory

    private val _currentTheme = MutableStateFlow(ThemeMode.valueOf(storageManager.getAppearance()))
    val currentTheme: StateFlow<ThemeMode> get() = _currentTheme

    private val _currentExpense = MutableStateFlow<Expense?>(null)
    val currentExpense: StateFlow<Expense?> get() = _currentExpense

    init {
        viewModelScope.launch {
            repository.budget.collect { budget ->
                _budgetState.value = budget
            }
        }
    }

    fun setTheme(themeMode: ThemeMode) {
        storageManager.setAppearance(themeMode)
        _currentTheme.value = themeMode
    }

    fun processBudget(
        currency: Currency,
        amount: Double,
        markFirstLaunch: Boolean = false
    ) {
        viewModelScope.launch {
            if (amount == 0.0) {
                _uiState.value = UiState.Error
            } else {
                val budgetEntity = BudgetEntity(
                    currency = currency,
                    amount = amount
                )
                viewModelScope.launch {
                    repository.insertBudget(
                        budgetEntity
                    )
                    _budgetState.value = budgetEntity
                }
                if (markFirstLaunch) {
                    CategoryInitializer.initializeCategories(repository)
                    storageManager.setFirstLaunch(false)
                }
                _uiState.value = UiState.Success
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            repository.categories.collect { categories ->
                _categories.value = categories
            }
        }
    }

    fun addCategory(
        name: String,
        color: CategoryColor?,
        icon: CategoryIcon?
    ) {
        viewModelScope.launch {
            if (name.isEmpty() || color == null || icon == null) {
                _uiState.value = UiState.Error
            } else {
                repository.insertCategory(
                    Category(
                        name = name,
                        color = color,
                        icon = icon
                    )
                )
                _uiState.value = UiState.Success
            }
        }
    }

    fun deleteCategory(
        categoryId: Int
    ) = viewModelScope.launch {
        val expenseCount = repository.countExpensesInCategory(categoryId)
        if (expenseCount > 0) {
            _displayDialog.value = true
            _deletingCategoryId.value = categoryId
        } else {
            repository.deleteCategoryById(categoryId)
            _categories.update { currentCategories ->
                currentCategories.filter { it.id != categoryId }
            }
        }
    }

    fun saveExpense(
        amount: Double,
        title: String,
        date: LocalDate,
        category: Category?
    ) {
        viewModelScope.launch {
            if (amount == 0.0 || title.isEmpty() || category == null) {
                _uiState.value = UiState.Error
            } else {
                repository.insertExpense(
                    ExpenseWithCategoryId(
                        title = title,
                        amount = amount,
                        date = date.toFormattedString(),
                        categoryId = category.id,
                    )
                )
                _uiState.value = UiState.Success
            }
        }
    }

    fun getExpenseById(expenseId: Int) {
        viewModelScope.launch {
            try {
                val expense = repository.getExpenseById(expenseId)
                _currentExpense.value = expense
            } catch (e: Exception) {
                _uiState.value = UiState.Error
            }
        }
    }

    fun updateExpenseById(
        expenseId: Int,
        amount: Double,
        title: String,
        date: LocalDate,
        category: Category
    ) {
        viewModelScope.launch {
            if (amount == 0.0 || title.isEmpty()) {
                _uiState.value = UiState.Error
                return@launch
            }

            try {
                repository.updateExpenseById(
                    expenseId = expenseId,
                    title = title,
                    amount = amount,
                    date = date.toFormattedString(),
                    categoryId = category.id
                )
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error
            }
        }
    }

    fun getExpensesForCurrentMonth() {
        viewModelScope.launch {
            val currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
            repository.getExpensesByMonth(currentMonth).collect { expenses ->
                _expenses.value = expenses
                _totalExpenses.value = expenses.sumOf { it.data.amount }.roundToTwoDecimalPlaces()
            }
        }
    }

    fun dismissDialog() {
        _displayDialog.value = false
    }

    fun updateCategoryBeforeDeleting(
        newCategoryId: Int
    ) {
        _displayDialog.value = false
        viewModelScope.launch {
            repository.deleteCategory(
                oldCategoryId = _deletingCategoryId.value,
                newCategoryId = newCategoryId
            )
        }
    }

    fun deleteExpensesAlongWithCategory() {
        _displayDialog.value = false
        viewModelScope.launch {
            repository.deleteCategoryIncludingExpenses(_deletingCategoryId.value)
            _categories.update { currentCategories ->
                currentCategories.filter { it.id != _deletingCategoryId.value }
            }
        }
    }

    fun setSelectedTab(tab: TabItem) {
        _selectedTab.value = tab
    }

    fun setOrUpdateTimeFrame(
        currentDate: LocalDate = LocalDate.now(),
        isInitialSetup: Boolean = true,
        toPrevious: Boolean = false
    ) {
        val baseDate = if (isInitialSetup) {
            currentDate
        } else {
            LocalDate.parse(selectedTimeFrame.value.startDate)
        }

        val (processedDate) = when (selectedTab.value) {
            TabItem.Weekly -> {
                val targetDate = when {
                    isInitialSetup -> currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                    toPrevious -> baseDate.minusWeeks(1)
                    else -> baseDate.plusWeeks(1)
                }

                val endOfWeek = targetDate.plusDays(6)

                val title = "${targetDate.format(DateTimeFormatter.ofPattern("MMM dd"))} - " +
                        endOfWeek.format(DateTimeFormatter.ofPattern("MMM dd"))

                Pair(
                    TimeFrame(
                        title = title,
                        startDate = targetDate.toString(),
                        endDate = endOfWeek.toString()
                    ),
                    true
                )
            }

            TabItem.Monthly -> {
                val targetDate = when {
                    isInitialSetup -> currentDate
                    toPrevious -> baseDate.minusMonths(1)
                    else -> baseDate.plusMonths(1)
                }

                val startOfMonth = targetDate.with(TemporalAdjusters.firstDayOfMonth())
                val endOfMonth = targetDate.with(TemporalAdjusters.lastDayOfMonth())

                Pair(
                    TimeFrame(
                        title = targetDate.format(DateTimeFormatter.ofPattern("MMM yyyy")),
                        startDate = startOfMonth.toString(),
                        endDate = endOfMonth.toString()
                    ),
                    true
                )
            }

            TabItem.Yearly -> {
                val targetDate = when {
                    isInitialSetup -> currentDate
                    toPrevious -> baseDate.minusYears(1)
                    else -> baseDate.plusYears(1)
                }

                val startOfYear = targetDate.with(TemporalAdjusters.firstDayOfYear())
                val endOfYear = targetDate.with(TemporalAdjusters.lastDayOfYear())

                Pair(
                    TimeFrame(
                        title = targetDate.format(DateTimeFormatter.ofPattern("yyyy")),
                        startDate = startOfYear.toString(),
                        endDate = endOfYear.toString()
                    ),
                    true
                )
            }
        }

        // Update the selected time frame
        _selectedTimeFrame.value = processedDate
        updateExpensesListForSelectedTimeFrame()
    }

    fun updateExpensesListForSelectedTimeFrame() {
        viewModelScope.launch {
            repository.getExpensesByTimeFrame(
                selectedTimeFrame.value.startDate,
                selectedTimeFrame.value.endDate
            ).collect { list ->
                _expensesByTimeFrame.value = list
                groupExpensesByCategory()
            }
        }
    }

    fun deleteExpense(id: Int) {
        viewModelScope.launch {
            repository.deleteExpenseById(id)
            _expenses.value = _expenses.value.filter { it.data.id != id }
        }
    }

    private fun groupExpensesByCategory() {
        val expenses = _expensesByTimeFrame.value
        val totalAmount = expenses.sumOf { it.data.amount }
        _expensesByCategory.value = expenses.groupBy { it.category }
            .map { (category, categoryExpenses) ->
                val categoryAmount = categoryExpenses.sumOf { it.data.amount }
                val percentage = (categoryAmount / totalAmount) * 100
                ExpenseBreakDown(category, categoryAmount, percentage.toFloat())
            }
    }

    fun filterExpensesByCategoryInTimeFrame(categoryId: Int) {
        val allExpenses = _expensesByTimeFrame.value
        val filteredExpenses = allExpenses.filter { it.data.categoryId == categoryId }
        _expensesByCategoryInTimeFrame.value = filteredExpenses
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }

    sealed class UiState {
        data object Success : UiState()
        data object Error : UiState()
        data object Idle : UiState()
    }
}