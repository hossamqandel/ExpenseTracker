package dev.hossam.expensetracker.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.hossam.expensetracker.feature_add_transaction.data.local.AddTransactionDao
import dev.hossam.expensetracker.feature_detail.data.local.TransactionDetailDao

@Database(
    version = DatabaseUtil.VERSION,
    exportSchema = false,
)
abstract class ExpenseTrackerDatabase : RoomDatabase() {

    abstract fun transactionDetailDao(): TransactionDetailDao
    abstract fun addTransactionDao(): AddTransactionDao
}