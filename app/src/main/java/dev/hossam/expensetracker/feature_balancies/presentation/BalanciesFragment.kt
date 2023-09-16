package dev.hossam.expensetracker.feature_balancies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.expensetracker.databinding.FragmentBalanciesBinding

@AndroidEntryPoint
class BalanciesFragment : Fragment() {

    private var _binding: FragmentBalanciesBinding? = null
    private val binding get() = _binding!!
    private val totalBalanceTitle by lazy { binding.layoutAmountTemplate.amountSubject }
    private val tvTotalBalance by lazy { binding.layoutAmountTemplate.amountValue }
    private val recyclerTransactions by lazy { binding.layoutRecyclerTransactionsTemplate.rvTransactions }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          _binding = FragmentBalanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}