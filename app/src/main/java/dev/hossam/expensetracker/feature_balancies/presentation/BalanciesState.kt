package dev.hossam.expensetracker.feature_balancies.presentation

data class BalanciesState(
    val totalBalance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
)
