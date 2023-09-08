package dev.hossam.expensetracker.feature_detail.data.repository

import dev.hossam.expensetracker.feature_detail.data.local.TransactionDetailDao
import dev.hossam.expensetracker.feature_detail.domain.repository.TransactionDetailRepository
import javax.inject.Inject

class TransactionDetailRepositoryImpl @Inject constructor(
    private val dao: TransactionDetailDao
) : TransactionDetailRepository {

    override suspend fun getTransactionDetail(id: Int): Any? {
        return dao.getTransactionDetailById(id = id)
    }

    override suspend fun deleteTransaction(id: Int) {
        return dao.deleteTransactionById(id = id)
    }

}