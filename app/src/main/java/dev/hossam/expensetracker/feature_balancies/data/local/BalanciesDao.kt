package dev.hossam.expensetracker.feature_balancies.data.local

import androidx.room.Dao
import androidx.room.Query
import dev.hossam.expensetracker.core.data.room.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanciesDao {

    @Query("SELECT SUM(amount) FROM TransactionEntity WHERE type = :type")
    suspend fun sumAllTransactionsByType(type: String): Int?

    @Query("SELECT * FROM TransactionEntity ORDER BY date DESC LIMIT :limit")
    fun getLimitedRecentlyTransactions(limit: Int): Flow<List<TransactionEntity>>
    
}