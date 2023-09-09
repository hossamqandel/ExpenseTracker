package dev.hossam.expensetracker.di

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.hossam.expensetracker.BaseApplication
import dev.hossam.expensetracker.core.data.room.DatabaseUtil
import dev.hossam.expensetracker.core.data.room.ExpenseTrackerDatabase
import dev.hossam.expensetracker.feature_add_transaction.data.local.AddTransactionDao
import dev.hossam.expensetracker.feature_detail.data.local.TransactionDetailDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: BaseApplication
    ): ExpenseTrackerDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = ExpenseTrackerDatabase::class.java,
            name = DatabaseUtil.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTransactionDetailDao(
        database: ExpenseTrackerDatabase
    ): TransactionDetailDao {
        return database.transactionDetailDao()
    }

    @Provides
    @Singleton
    fun provideAddTransactionDao(
        database: ExpenseTrackerDatabase
    ): AddTransactionDao {
        return database.addTransactionDao()
    }

}