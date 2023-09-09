package dev.hossam.expensetracker.core.data.room.base_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.hossam.expensetracker.core.data.room.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BaseTransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(entity: TransactionEntity)

    @Query("SELECT * FROM TransactionEntity WHERE type = :type ORDER BY Date DESC")
    fun getAllTransactionsByType(type: String): Flow<List<TransactionEntity>>

    @Query("DELETE FROM TransactionEntity WHERE id = :id")
    suspend fun deleteTransactionById(id: Int)

}