package dev.hossam.expensetracker.feature_balancies.domain

import dev.hossam.expensetracker.core.core_actions.TransactionDeleter
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import kotlinx.coroutines.flow.Flow

interface BalanciesRepository : TransactionDeleter {

    suspend fun getUserBalance(): Int
    suspend fun getSumOfAllIncomeTransactions(): Int
    suspend fun getSumOfAllExpenseTransactions(): Int
    fun getRecentlyTransactions(limit: Int): Flow<List<TransactionDTO>>
    override suspend fun deleteTransactionById(id: Int)
}