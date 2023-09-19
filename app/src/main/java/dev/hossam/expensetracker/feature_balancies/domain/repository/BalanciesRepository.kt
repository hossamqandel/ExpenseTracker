package dev.hossam.expensetracker.feature_balancies.domain.repository

import dev.hossam.expensetracker.core.core_actions.TransactionDeleter
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import kotlinx.coroutines.flow.Flow

interface BalanciesRepository : TransactionDeleter {

    suspend fun getUserBalance(): Double
    suspend fun getSumOfAllIncomeTransactions(): Double
    suspend fun getSumOfAllExpenseTransactions(): Double
    fun getRecentlyTransactions(limit: Int): Flow<List<TransactionDTO>>
    override suspend fun deleteTransactionById(id: Int)
}