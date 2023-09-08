package dev.hossam.expensetracker.feature_detail.data.local

import androidx.room.Dao
import dev.hossam.expensetracker.core.core_actions.TransactionDeleter

@Dao
interface TransactionDetailDao : TransactionDeleter {

//    @Query("SELECT FROM TABLE WHERE id = :id")
    suspend fun getTransactionDetailById(id: Int): Any? //Temporary return

//    @Query("DELETE FROM Table WHERE id = :id")
    override suspend fun deleteTransactionById(id: Int)
}