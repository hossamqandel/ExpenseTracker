package dev.hossam.expensetracker.feature_detail.domain.repository

interface TransactionDetailRepository {

    suspend fun getTransactionDetail(id: Int): Any?
    suspend fun deleteTransaction(id: Int)
}