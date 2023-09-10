package dev.hossam.expensetracker.feature_detail.domain.repository

import dev.hossam.expensetracker.core.core_actions.TransactionDeleter
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO

interface TransactionDetailRepository : TransactionDeleter {

    suspend fun getTransactionDetail(id: Int): TransactionDTO?
    override suspend fun deleteTransactionById(id: Int)

}