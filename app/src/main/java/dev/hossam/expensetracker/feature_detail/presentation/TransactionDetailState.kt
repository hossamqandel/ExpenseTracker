package dev.hossam.expensetracker.feature_detail.presentation

data class TransactionDetailState(
    val id: Int = -1,
    val title: String = "",
    val amount: Double? = null,
    val type: String = "",
    val category: String = "",
    val date: String = "",
    val note: String? = null,
)
