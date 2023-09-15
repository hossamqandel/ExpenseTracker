package dev.hossam.expensetracker.feature_add_transaction.domain.use_case

import android.content.Context
import dev.hossam.expensetracker.R
import dev.hossam.expensetracker.core.android_util.Validation
import javax.inject.Inject

class ValidateTitleUseCase @Inject constructor(
    private val context: Context
) {

    operator fun invoke(value: String): Validation{
        if (value.isBlank()){
            return Validation(isValid = false, errorMessage = context.getString(R.string.title_cant_be_empty))
        }
        return Validation(isValid = true, errorMessage = null, value = value)
    }
}