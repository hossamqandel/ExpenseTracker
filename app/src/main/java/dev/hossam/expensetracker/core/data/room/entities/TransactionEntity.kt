package dev.hossam.expensetracker.core.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TransactionEntity")
data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int? = null,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("amount")
    val amount: Double?,

    @ColumnInfo("type")
    val type: String,

    @ColumnInfo("category")
    val category: String,

    @ColumnInfo("date")
    val date: Long,

    @ColumnInfo("note")
    val note: String?,
)