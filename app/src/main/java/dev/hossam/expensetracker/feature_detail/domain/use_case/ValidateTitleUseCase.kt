package dev.hossam.expensetracker.feature_detail.domain.use_case

import dev.hossam.expensetracker.core.android_util.Validate

class ValidateTitleUseCase {

    operator fun invoke(value: String): Validate{
        if (value.isBlank()){
            return Validate(isValid = false, errorMessage = "Title can't be empty")
        }
        return Validate(isValid = true, errorMessage = null, value = value)
    }
}