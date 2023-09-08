package dev.hossam.expensetracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dev.hossam.expensetracker.core.adapters.BaseTransactionAdapter

@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {

    @Provides
    @FragmentScoped
    fun provideBaseTransactionAdapter(): BaseTransactionAdapter {
        return BaseTransactionAdapter()
    }

}