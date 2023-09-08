package dev.hossam.expensetracker.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    version = DatabaseUtil.VERSION,
    exportSchema = false,
)
abstract class ExpenseTrackerDatabase : RoomDatabase() {
}