package dev.hossam.expensetracker.feature_balancies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.expensetracker.R
import dev.hossam.expensetracker.core.adapters.BaseTransactionAdapter
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import dev.hossam.expensetracker.databinding.FragmentBalanciesBinding
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun linkTextFieldsWithStringResource(){
        totalBalanceTitle.text = getString(R.string.total_balance)
    }

    private fun collectBalanciesState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(CREATED){
                balanciesViewModel.state.collectLatest { balanciesState ->
                    setBalanciesDataOnUi(balanciesState)
                }
            }
        }
    }

    private fun setBalanciesDataOnUi(state: BalanciesState){
        tvTotalBalance.text = "$".plus(state.totalBalance)
        binding.tvIncomeAmount.text = "$".plus(state.totalIncome)
        binding.tvExpenseAmount.text = "$".plus(state.totalExpense)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun collectRecentTransactionsState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(CREATED){
                recentTransactionsViewModel.state.mapLatest { recentTransactionsState ->
                    bindRecyclerDataOnUi(recentTransactionsState)
                }.collect()
            }
        }
    }

    private fun bindRecyclerDataOnUi(recentTransactions: List<TransactionDTO>){
        transactionsAdapter.setTransactions(recentTransactions)
        recyclerTransactions.adapter = transactionsAdapter
    }



}