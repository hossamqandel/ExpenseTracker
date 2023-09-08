package dev.hossam.expensetracker.core.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.hossam.expensetracker.R

class BaseTransactionVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val transIcon: ImageView = itemView.findViewById(R.id.iv_transactionType)
    val transTitle: TextView = itemView.findViewById(R.id.tv_TransactionTitle)
    val transType: TextView = itemView.findViewById(R.id.tv_TransactionType)
    val transAmount: TextView = itemView.findViewById(R.id.tv_TransactionAmount)
    val transDate: TextView = itemView.findViewById(R.id.tv_TransactionDate)

//    fun bind(transactions: List<Int>) {
//        val currentItem = transactions[adapterPosition]
////        transIcon.drawable = -----
//        transTitle.text = ""
//        transType.text = ""
//        transAmount.text = ""
//        transDate.text = ""
//    }
}