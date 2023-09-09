package dev.hossam.expensetracker.feature_add_transaction.data.repository

import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import dev.hossam.expensetracker.core.data.room.entities.TransactionEntity
import dev.hossam.expensetracker.core.data.room.entities.TransactionMapper
import dev.hossam.expensetracker.feature_add_transaction.data.local.AddTransactionDao
import dev.hossam.expensetracker.feature_add_transaction.domain.repository.AddTransactionRepository
import javax.inject.Inject

class AddTransactionRepositoryImpl @Inject constructor(
    private val dao: AddTransactionDao
): AddTransactionRepository {
    override suspend fun addTransaction(transactionDTO: TransactionDTO) {
        val transactionEntity: TransactionEntity = TransactionMapper.toEntity(dto = transactionDTO)
        dao.insertTransaction(entity = transactionEntity)
    }

    override suspend fun deleteTransactionById(id: Int) {
        TODO("Not yet implemented")
    }


}