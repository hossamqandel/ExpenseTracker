package dev.hossam.expensetracker.feature_balancies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.expensetracker.R
import dev.hossam.expensetracker.databinding.FragmentBalanciesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BalanciesFragment : Fragment() {

    private var _binding: FragmentBalanciesBinding? = null
    private val binding get() = _binding!!
    private val balanciesViewModel: BalanciesViewModel by viewModels()
    private val totalBalanceTitle by lazy { binding.layoutAmountTemplate.amountSubject }
    private val tvTotalBalance by lazy { binding.layoutAmountTemplate.amountValue }

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

}