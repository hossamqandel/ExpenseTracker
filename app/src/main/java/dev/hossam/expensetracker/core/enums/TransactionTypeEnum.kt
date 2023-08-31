package dev.hossam.expensetracker.core.enums

import java.util.Arrays
import java.util.stream.Collectors


enum class TransactionTypeEnum(private val value: String) {

    INCOME("Income"),
    EXPENSE("Expense");

    fun getAllTransactionTypeEnums(): List<String> {
        return Arrays.stream(TransactionTypeEnum.values())
            .map { type -> type.value }
            .collect(Collectors.toList())
    }
}