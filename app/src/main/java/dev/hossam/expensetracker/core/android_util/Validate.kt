package dev.hossam.expensetracker.core.android_util

data class Validate(
    val isValid: Boolean? = null,
    val errorMessage: String? = null,
    val value: String = ""
)
