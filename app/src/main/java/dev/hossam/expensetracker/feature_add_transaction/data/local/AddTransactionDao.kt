package dev.hossam.expensetracker.feature_add_transaction.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import dev.hossam.expensetracker.core.data.room.entities.TransactionEntity

@Dao
interface AddTransactionDao {

    //Tobe added in base dao since detail screen and add screen will use it in both screens
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertTransaction(entity: TransactionEntity)
}