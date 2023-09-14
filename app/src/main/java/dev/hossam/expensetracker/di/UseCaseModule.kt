package dev.hossam.expensetracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.hossam.expensetracker.feature_detail.domain.use_case.ValidateAmountUseCase
import dev.hossam.expensetracker.feature_detail.domain.use_case.ValidateCategoryUseCase
import dev.hossam.expensetracker.feature_detail.domain.use_case.ValidateTitleUseCase
import dev.hossam.expensetracker.feature_detail.domain.use_case.ValidateTransactionTypeUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideValidateTitleUseCase() = ValidateTitleUseCase()

    @Provides
    @ViewModelScoped
    fun provideValidateAmountUseCase() = ValidateAmountUseCase()

    @Provides
    @ViewModelScoped
    fun provideValidateTypeUseCase() = ValidateTransactionTypeUseCase()

    @Provides
    @ViewModelScoped
    fun provideValidateCategoryUseCase() = ValidateCategoryUseCase()
}