package dev.hossam.expensetracker.core.core_actions

import dev.hossam.expensetracker.core.enums.TransactionTypeEnum

interface TransactionRetriever {

    fun getAllTransactionsByType(type: TransactionTypeEnum): List<Unit>
}