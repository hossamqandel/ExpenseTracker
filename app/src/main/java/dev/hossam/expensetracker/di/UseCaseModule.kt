package dev.hossam.expensetracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dev.hossam.expensetracker.feature_add_transaction.domain.use_case.ValidateAmountUseCase
import dev.hossam.expensetracker.feature_add_transaction.domain.use_case.ValidateCategoryUseCase
import dev.hossam.expensetracker.feature_add_transaction.domain.use_case.ValidateDateUseCase
import dev.hossam.expensetracker.feature_add_transaction.domain.use_case.ValidateTitleUseCase
import dev.hossam.expensetracker.feature_add_transaction.domain.use_case.ValidateTransactionTypeUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideValidateTitleUseCase(@ApplicationContext context: Context) =
        ValidateTitleUseCase(context = context)

    @Provides
    @ViewModelScoped
    fun provideValidateAmountUseCase(@ApplicationContext context: Context) =
        ValidateAmountUseCase(context = context)

    @Provides
    @ViewModelScoped
    fun provideValidateTypeUseCase(@ApplicationContext context: Context) =
        ValidateTransactionTypeUseCase(context = context)

    @Provides
    @ViewModelScoped
    fun provideValidateCategoryUseCase(@ApplicationContext context: Context) =
        ValidateCategoryUseCase(context = context)

    @Provides
    @ViewModelScoped
    fun provideValidateDateUseCase(@ApplicationContext context: Context) =
        ValidateDateUseCase(context = context)
}