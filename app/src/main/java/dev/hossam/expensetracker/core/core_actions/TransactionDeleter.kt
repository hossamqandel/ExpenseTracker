package dev.hossam.expensetracker.core.core_actions

interface TransactionDeleter {

    fun deleteTransactionById(id: Int)
}