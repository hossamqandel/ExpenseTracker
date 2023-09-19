package dev.hossam.expensetracker.feature_balancies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import dev.hossam.expensetracker.feature_balancies.domain.repository.BalanciesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Collections.emptyList
import javax.inject.Inject

@HiltViewModel
class RecentTransactionsViewModel @Inject constructor(
    private val repo: BalanciesRepository
) :ViewModel() {

    private var recentTransactionsJob: Job? = null

    private val _state = MutableStateFlow<List<TransactionDTO>>(emptyList())
    val state = _state.asStateFlow()

    init {
        getRecentTransactions()
    }

    private fun getRecentTransactions(){
        recentTransactionsJob?.cancel()
        recentTransactionsJob = repo.getRecentlyTransactions(5).onEach { transactions ->
            _state.value = transactions
        }.launchIn(viewModelScope)
    }

}