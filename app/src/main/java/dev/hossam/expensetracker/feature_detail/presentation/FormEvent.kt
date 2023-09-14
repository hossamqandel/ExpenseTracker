package dev.hossam.expensetracker.feature_detail.presentation

sealed interface FormEvent {
    data class Title(val value: String): FormEvent
    data class Amount(val value: String): FormEvent
    data class TransactionType(val value: String): FormEvent
    data class Category(val value: String): FormEvent
    object Save : FormEvent
}
