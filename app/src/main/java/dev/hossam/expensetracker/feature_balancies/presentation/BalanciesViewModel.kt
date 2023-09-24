package dev.hossam.expensetracker.feature_balancies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hossam.expensetracker.feature_balancies.domain.repository.BalanciesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanciesViewModel @Inject constructor(
    private val repo: BalanciesRepository
) : ViewModel() {

    private var balanceJob: Job? = null
    private val _state = MutableStateFlow(BalanciesState())
    val state = _state.asStateFlow()

    init {
        getAllBalancies()
    }

    fun getAllBalancies() {
        balanceJob?.cancel()
        balanceJob = viewModelScope.launch {
            val totalIncome = repo.getSumOfAllIncomeTransactions()
            val totalExpense = repo.getSumOfAllExpenseTransactions()
            val totalBalance = repo.getUserBalance()
            _state.value = state.value.copy(
                totalBalance = totalBalance,
                totalIncome = totalIncome,
                totalExpense = totalExpense
            )
        }
    }

}