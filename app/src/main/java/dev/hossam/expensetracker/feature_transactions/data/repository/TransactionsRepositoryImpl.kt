package dev.hossam.expensetracker.feature_transactions.data.repository

import dev.hossam.expensetracker.core.data.room.base_dao.BaseTransactionDao
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import dev.hossam.expensetracker.core.data.room.entities.TransactionMapper
import dev.hossam.expensetracker.feature_transactions.data.local.TransactionsDao
import dev.hossam.expensetracker.feature_transactions.domain.repository.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val transactionsDao: TransactionsDao,
    private val baseDao: BaseTransactionDao,
) : TransactionsRepository {

    override fun getAllTransactionsByType(type: String): Flow<List<TransactionDTO>> {
        return transactionsDao.getAllTransactionsByTypeOrderByDesc(type = type)
            .map { transEntities -> transEntities.map { transEntity ->
                TransactionMapper.fromEntity(transEntity)
            }
        }
    }

    override suspend fun deleteTransactionById(id: Int) {
        baseDao.deleteTransactionById(id = id)
    }

}