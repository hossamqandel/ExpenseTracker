package dev.hossam.expensetracker.core.data.room.dto

import com.google.gson.annotations.SerializedName

data class TransactionDTO(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("title")
    val title: String,

    @SerializedName("amount")
    val amount: Double?,

    @SerializedName("type")
    val type: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("date")
    val date: Long,

    @SerializedName("note")
    val note: String?,
)