package dev.hossam.expensetracker.feature_transactions.data.local

import androidx.room.Dao
import androidx.room.Query
import dev.hossam.expensetracker.core.data.room.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM TransactionEntity WHERE type = :type ORDER BY date DESC")
    fun getAllTransactionsByTypeOrderByDesc(type: String): Flow<List<TransactionEntity>>
}