package dev.hossam.expensetracker.feature_add_transaction.domain.use_case

import android.content.Context
import androidx.core.text.isDigitsOnly
import dev.hossam.expensetracker.R
import dev.hossam.expensetracker.core.android_util.Validation
import javax.inject.Inject

class ValidateAmountUseCase @Inject constructor(
    private val context: Context
) {

    operator fun invoke(value: String): Validation{
        if (value.isBlank()){
            return Validation(
                isValid = false,
                errorMessage = context.getString(R.string.amount_cant_be_empty)
            )
        }

        if (value.isDigitsOnly() && value.toDouble() <= 0.0){
            return Validation(
                isValid = false,
                errorMessage = context.getString(R.string.amount_should_be_bigger_than_zero)
            )
        }
        return Validation(isValid = true, errorMessage = null, value = value)
    }
}