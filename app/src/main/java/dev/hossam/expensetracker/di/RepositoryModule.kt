package dev.hossam.expensetracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.hossam.expensetracker.feature_add_transaction.data.repository.AddTransactionRepositoryImpl
import dev.hossam.expensetracker.feature_add_transaction.domain.repository.AddTransactionRepository
import dev.hossam.expensetracker.feature_balancies.data.repository.BalanciesRepositoryImpl
import dev.hossam.expensetracker.feature_balancies.domain.repository.BalanciesRepository
import dev.hossam.expensetracker.feature_detail.data.repository.TransactionDetailRepositoryImpl
import dev.hossam.expensetracker.feature_detail.domain.repository.TransactionDetailRepository
import dev.hossam.expensetracker.feature_transactions.data.repository.TransactionsRepositoryImpl
import dev.hossam.expensetracker.feature_transactions.domain.repository.TransactionsRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindTransactionDetailRepo(
        repo: TransactionDetailRepositoryImpl
    ): TransactionDetailRepository

    @Binds
    @ViewModelScoped
    abstract fun bindAddTransactionRepo(
        repo: AddTransactionRepositoryImpl
    ): AddTransactionRepository

    @Binds
    @ViewModelScoped
    abstract fun bindABalanciesRepo(
        repo: BalanciesRepositoryImpl
    ): BalanciesRepository

    @Binds
    @ViewModelScoped
    abstract fun bindTransactionsRepo(
        repo: TransactionsRepositoryImpl
    ): TransactionsRepository

}