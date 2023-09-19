package dev.hossam.expensetracker.core.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.hossam.expensetracker.R
import dev.hossam.expensetracker.core.android_util.TransactionCategoryIcon
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import dev.hossam.expensetracker.core.enums.TransactionTypeEnum.EXPENSE
import dev.hossam.expensetracker.core.enums.TransactionTypeEnum.INCOME
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BaseTransactionVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    val date by lazy {  Date() }// You can replace this with your specific date
    val sdf by lazy { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    private val transIcon: ImageView = itemView.findViewById(R.id.iv_transactionType)
    private val transTitle: TextView = itemView.findViewById(R.id.tv_TransactionTitle)
    private val transCategory: TextView = itemView.findViewById(R.id.tv_TransactionCategory)
    private val transAmount: TextView = itemView.findViewById(R.id.tv_TransactionAmount)
    private val transDate: TextView = itemView.findViewById(R.id.tv_TransactionDate)

    fun bind(transactions: List<TransactionDTO>) {
        transactions[adapterPosition].apply {
            transTitle.text = title
            transCategory.text = category
            setTextAmountColor(type)
            transAmount.text = "$$amount"
            transDate.text = sdf.format(date)
            transIcon.setImageResource(
                TransactionCategoryIcon.categoryIconMap[category]!!
            )
        }
    }

    private fun setTextAmountColor(type: String){
        when(type){
            INCOME.value ->
                transAmount.setTextColor(ContextCompat.getColor(
                    itemView.context,
                    R.color.text_income)
                )
            EXPENSE.value ->
                transAmount.setTextColor(ContextCompat.getColor(
                    itemView.context,
                    R.color.text_expense)
                )
        }
    }


}