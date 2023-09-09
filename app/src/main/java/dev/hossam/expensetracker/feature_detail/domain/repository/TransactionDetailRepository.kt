package dev.hossam.expensetracker.feature_detail.domain.repository

import dev.hossam.expensetracker.core.core_actions.TransactionDeleter

interface TransactionDetailRepository : TransactionDeleter {

    suspend fun getTransactionDetail(id: Int): Any?
    override suspend fun deleteTransactionById(id: Int)

}