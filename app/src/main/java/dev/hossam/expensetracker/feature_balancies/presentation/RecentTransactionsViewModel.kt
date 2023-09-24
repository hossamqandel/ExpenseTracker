package dev.hossam.expensetracker.feature_balancies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import dev.hossam.expensetracker.feature_add_transaction.domain.repository.AddTransactionRepository
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
    private val balanceRepo: BalanciesRepository,
    private val addTransRepo: AddTransactionRepository,
) :ViewModel() {

    private var recentTransactionsJob: Job? = null
    private var recentlyDeletedTransaction: TransactionDTO? = null

    private val _state = MutableStateFlow<List<TransactionDTO>>(emptyList())
    val state = _state.asStateFlow()

    init {
        getRecentTransactions()
    }

    private fun getRecentTransactions(){
        recentTransactionsJob?.cancel()
        recentTransactionsJob = balanceRepo.getRecentlyTransactions(5).onEach { transactions ->
            _state.value = transactions
        }.launchIn(viewModelScope)
    }

    fun removeTransaction(transaction: TransactionDTO?) = viewModelScope.launch {
        recentlyDeletedTransaction = transaction
        transaction?.let { balanceRepo.deleteTransactionById(id = it.id!!) }
    }

    fun restoreTransaction() {
        viewModelScope.launch {
            println(Thread.currentThread())
            recentlyDeletedTransaction?.let { addTransRepo.addTransaction(transactionDTO = it) }
        }
    }
}