package dev.hossam.expensetracker.core.core_actions

import kotlinx.coroutines.flow.Flow

interface TransactionRetriever {

    fun getAllTransactionsByType(type: String): Flow<List<Unit>>
}