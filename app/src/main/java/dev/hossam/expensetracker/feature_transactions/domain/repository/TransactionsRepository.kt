package dev.hossam.expensetracker.feature_transactions.domain.repository

import dev.hossam.expensetracker.core.core_actions.TransactionDeleter
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository : TransactionDeleter {

    fun getAllTransactionsByType(type: String): Flow<List<TransactionDTO>>
    override suspend fun deleteTransactionById(id: Int)

}