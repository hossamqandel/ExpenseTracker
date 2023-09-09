package dev.hossam.expensetracker.feature_add_transaction.domain.repository

import dev.hossam.expensetracker.core.core_actions.TransactionCreator
import dev.hossam.expensetracker.core.core_actions.TransactionDeleter
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO

interface AddTransactionRepository : TransactionDeleter, TransactionCreator {

    override suspend fun addTransaction(transactionDTO: TransactionDTO)
    override suspend fun deleteTransactionById(id: Int)
}