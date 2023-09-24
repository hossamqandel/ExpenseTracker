package dev.hossam.expensetracker.feature_balancies.presentation

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.expensetracker.R
import dev.hossam.expensetracker.core.adapters.BaseTransactionAdapter
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import dev.hossam.expensetracker.core.extension.showSnackBar
import dev.hossam.expensetracker.databinding.FragmentBalanciesBinding
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BalanciesFragment : Fragment() {

    private var _binding: FragmentBalanciesBinding? = null
    private val binding get() = _binding!!
    private val balanciesViewModel: BalanciesViewModel by viewModels()
    private val recentTransactionsViewModel: RecentTransactionsViewModel by viewModels()
    private var simpleCallback: ItemTouchHelper.SimpleCallback? = null
    private var itemTouchHelper: ItemTouchHelper? = null

    private val totalBalanceTitle by lazy { binding.layoutAmountTemplate.amountSubject }
    private val tvTotalBalance by lazy { binding.layoutAmountTemplate.amountValue }
    private val recyclerTransactions by lazy { binding.layoutRecyclerTransactionsTemplate.rvTransactions }
    private val transactionsAdapter by lazy { BaseTransactionAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBalanciesBinding.inflate(inflater, container, false)
        linkTextFieldsWithStringResource()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectBalanciesState()
        collectRecentTransactionsState()
        recyclerSwipeActions()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun linkTextFieldsWithStringResource() {
        totalBalanceTitle.text = getString(R.string.total_balance)
    }

    private fun collectBalanciesState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(CREATED) {
                balanciesViewModel.state.collectLatest { balanciesState ->
                    setBalanciesDataOnUi(balanciesState)
                }
            }
        }
    }

    private fun setBalanciesDataOnUi(state: BalanciesState) {
        tvTotalBalance.text = "$".plus(state.totalBalance)
        binding.tvIncomeAmount.text = "$".plus(state.totalIncome)
        binding.tvExpenseAmount.text = "$".plus(state.totalExpense)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun collectRecentTransactionsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(CREATED) {
                recentTransactionsViewModel.state.mapLatest { recentTransactionsState ->
                    bindRecyclerDataOnUi(recentTransactionsState)
                }.collect()
            }
        }
    }

    private fun bindRecyclerDataOnUi(recentTransactions: List<TransactionDTO>) {
        transactionsAdapter.setTransactions(recentTransactions)
        recyclerTransactions.adapter = transactionsAdapter
    }


    private fun recyclerSwipeActions() {
        simpleCallback = object : SimpleCallback(0, LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.layoutPosition
                when (direction) {
                    LEFT -> onSwipeToRemoveTransaction(position = position)
                }
            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                context?.let {
                    RecyclerViewSwipeDecorator.Builder(
                        canvas, recyclerView,
                        viewHolder,
                        dX, dY,
                        actionState, isCurrentlyActive
                    ).addSwipeLeftBackgroundColor(resources.getColor(R.color.text_expense, it.theme)
                    ).addSwipeLeftActionIcon(R.drawable.arrow_left)
                        .create()
                        .decorate()
                }
                super.onChildDraw(
                    canvas, recyclerView, viewHolder,
                    dX, dY,
                    actionState, isCurrentlyActive
                )
            }
        }

        itemTouchHelper = ItemTouchHelper(simpleCallback!!)
        itemTouchHelper?.attachToRecyclerView(recyclerTransactions)
    }

    private fun onSwipeToRemoveTransaction(position: Int) {
        val itemToRemove = transactionsAdapter.getTransactionItem(position = position)
        itemToRemove?.let { tranDTO: TransactionDTO ->
            recentTransactionsViewModel.removeTransaction(transaction = tranDTO)
        }.also {
            balanciesViewModel.getAllBalancies()
            restoreCurrentRemovedTransaction()
        }
    }

    private fun restoreCurrentRemovedTransaction() {
        context?.let {
            it.applicationContext.showSnackBar(
                view = requireView(),
                message = R.string.transaction_has_been_successfully_restored,
                buttonTitle = R.string.undo
            ) {
                recentTransactionsViewModel.restoreTransaction()
                balanciesViewModel.getAllBalancies()
            }
        }
    }

}