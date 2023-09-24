package dev.hossam.expensetracker.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.hossam.expensetracker.R
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import java.util.Collections


class BaseTransactionAdapter : RecyclerView.Adapter<BaseTransactionVH>() {

    private var data = Collections.emptyList<TransactionDTO>()

    fun setTransactions(data: List<TransactionDTO>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseTransactionVH {
        val viewHolder = LayoutInflater.from(
            parent.context
        ).inflate(
            R.layout.item_transaction,
            parent,
            false
        )
        return BaseTransactionVH(viewHolder)
    }

    override fun onBindViewHolder(holder: BaseTransactionVH, position: Int) {
        holder.bind(transactions = data)
    }

    override fun getItemCount(): Int = data.size

    fun getTransactionItem(position: Int): TransactionDTO?{
        return data[position]
    }

}