package dev.hossam.expensetracker.feature_detail.domain.use_case

import androidx.core.text.isDigitsOnly
import dev.hossam.expensetracker.core.android_util.Validate

class ValidateAmountUseCase {

    operator fun invoke(value: String): Validate{
        if (value.isBlank()){
            return Validate(isValid = false, errorMessage = "Amount can't be empty")
        }
        if (!value.isDigitsOnly()){
            return Validate(isValid = false, errorMessage = "Amount should be only contains numbers")
        }
        if (value.isDigitsOnly() && value.toDouble() <= 0.0){
            return Validate(isValid = false, errorMessage = "Amount should be bigger than zero")
        }
        return Validate(isValid = true, errorMessage = null, value = value)
    }
}