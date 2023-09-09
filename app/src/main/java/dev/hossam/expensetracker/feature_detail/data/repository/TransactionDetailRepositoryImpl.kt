package dev.hossam.expensetracker.feature_detail.data.repository

import dev.hossam.expensetracker.core.data.room.base_dao.BaseTransactionDao
import dev.hossam.expensetracker.core.data.room.entities.TransactionEntity
import dev.hossam.expensetracker.feature_detail.data.local.TransactionDetailDao
import dev.hossam.expensetracker.feature_detail.domain.repository.TransactionDetailRepository
import javax.inject.Inject

class TransactionDetailRepositoryImpl @Inject constructor(
    private val baseDao: BaseTransactionDao,
    private val dao: TransactionDetailDao
) : TransactionDetailRepository {

    override suspend fun getTransactionDetail(id: Int): TransactionEntity? {
        return dao.getTransactionDetailById(id = id)
    }

    override suspend fun deleteTransactionById(id: Int) {
        return baseDao.deleteTransactionById(id = id)
    }

}