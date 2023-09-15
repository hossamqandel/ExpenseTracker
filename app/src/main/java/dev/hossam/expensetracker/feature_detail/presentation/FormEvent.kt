package dev.hossam.expensetracker.feature_detail.presentation

sealed interface FormEvent {
    data class Title(val value: String): FormEvent
    data class Amount(val value: String): FormEvent
    data class TransactionType(val value: String): FormEvent
    data class Category(val value: String): FormEvent
    data class Date(val value: Long?): FormEvent
    data class Note(val value: String? = null): FormEvent
    object Save : FormEvent
}
