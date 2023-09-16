package dev.hossam.expensetracker.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.hossam.expensetracker.core.data.room.base_dao.BaseTransactionDao
import dev.hossam.expensetracker.core.data.room.entities.TransactionEntity
import dev.hossam.expensetracker.feature_add_transaction.data.local.AddTransactionDao
import dev.hossam.expensetracker.feature_balancies.data.local.BalanciesDao
import dev.hossam.expensetracker.feature_detail.data.local.TransactionDetailDao

@Database(
    entities = [TransactionEntity::class],
    version = DatabaseUtil.VERSION,
    exportSchema = false,
)
abstract class ExpenseTrackerDatabase : RoomDatabase() {

    abstract fun transactionDetailDao(): TransactionDetailDao
    abstract fun addTransactionDao(): AddTransactionDao
    abstract fun baseTransactionDao(): BaseTransactionDao
    abstract fun balanciesDao(): BalanciesDao

}