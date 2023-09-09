package dev.hossam.expensetracker.core.core_actions

import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO

interface TransactionCreator {

    suspend fun addTransaction(transactionDTO: TransactionDTO)
}