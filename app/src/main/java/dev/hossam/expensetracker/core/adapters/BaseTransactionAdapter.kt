package dev.hossam.expensetracker.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.hossam.expensetracker.R
import java.util.Collections


class BaseTransactionAdapter : RecyclerView.Adapter<BaseTransactionVH>() {

    private var data = Collections.emptyList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseTransactionVH {
        val viewHolder = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_transaction,
            parent,
            false)
        return BaseTransactionVH(viewHolder)
    }

    override fun onBindViewHolder(holder: BaseTransactionVH, position: Int) {
    }

    override fun getItemCount(): Int = data.size


}