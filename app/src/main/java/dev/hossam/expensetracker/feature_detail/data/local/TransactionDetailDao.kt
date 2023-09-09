package dev.hossam.expensetracker.feature_detail.data.local

import androidx.room.Dao
import androidx.room.Query
import dev.hossam.expensetracker.core.core_actions.TransactionDeleter
import dev.hossam.expensetracker.core.data.room.entities.TransactionEntity

@Dao
interface TransactionDetailDao {

    @Query("SELECT * FROM TransactionEntity WHERE id = :id")
    suspend fun getTransactionDetailById(id: Int): TransactionEntity?
}