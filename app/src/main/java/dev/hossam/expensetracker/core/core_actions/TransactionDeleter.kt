package dev.hossam.expensetracker.core.core_actions

interface TransactionDeleter {

    suspend fun deleteTransactionById(id: Int)
}