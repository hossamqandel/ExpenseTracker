package dev.hossam.expensetracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dev.hossam.expensetracker.feature_detail.data.repository.TransactionDetailRepositoryImpl
import dev.hossam.expensetracker.feature_detail.domain.repository.TransactionDetailRepository

@Module
@InstallIn(ViewModelScoped::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindTransactionDetailRepo(
        repo: TransactionDetailRepositoryImpl
    ): TransactionDetailRepository

}