package dev.hossam.expensetracker.feature_detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.expensetracker.R
import dev.hossam.expensetracker.databinding.FragmentDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val getDetailViewModel: GetDetailViewModel by viewModels()
    private val updateTransactionViewModel: UpdateTransactionViewModel by viewModels()
    private val titleSubject by lazy { binding.layoutMemberTitle.tvSubject }
    private val titleValue by lazy { binding.layoutMemberTitle.tvSubjectValue }
    private val amountSubject by lazy { binding.layoutMemberAmount.tvSubject }
    private val amountValue by lazy { binding.layoutMemberAmount.tvSubjectValue }
    private val typeSubject by lazy { binding.layoutMemberType.tvSubject }
    private val typeValue by lazy { binding.layoutMemberType.tvSubjectValue }
    private val categorySubject by lazy { binding.layoutMemberCategory.tvSubject }
    private val categoryValue by lazy { binding.layoutMemberCategory.tvSubjectValue }
    private val dateSubject by lazy { binding.layoutMemberDate.tvSubject }
    private val dateValue by lazy { binding.layoutMemberDate.tvSubjectValue }
    private val noteSubject by lazy { binding.layoutMemberNote.tvSubject }
    private val noteValue by lazy { binding.layoutMemberNote.tvSubjectValue }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        setupSubjectsStringsFromResources()
        displayTextFieldsInPresentationMode()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeMode()
        collectUiState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSubjectsStringsFromResources(){
        titleSubject.text = getString(R.string.title)
        amountSubject.text = getString(R.string.amount)
        typeSubject.text = getString(R.string.transaction_type)
        categorySubject.text = getString(R.string.category)
        dateSubject.text = getString(R.string.date)
        noteSubject.text = getString(R.string.note)
    }

    private fun collectUiState(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                getDetailViewModel.state.collectLatest {
                    withContext(Dispatchers.Main){
                        bindDataToUI(detailState = it)
                    }
                }

            }
        }
    }
    private fun bindDataToUI(detailState: TransactionDetailState?){
        detailState?.let { transDetailState ->
            transDetailState.apply {
                titleValue.setText(title)
                amountValue.setText(amount.toString().plus("$"))
                typeValue.setText(type)
                categoryValue.setText(category)
                dateValue.setText(date)
                noteValue.setText(note)
            }
        }
    }
    private fun changeMode(){
        binding.btnChangeViewMode.setOnClickListener {
            updateTransactionViewModel.isInViewMode = !updateTransactionViewModel.isInViewMode
            val isInViewMode = updateTransactionViewModel.isInViewMode
            if (isInViewMode) {
                displayTextFieldsInEditMode()
                binding.btnChangeViewMode.text = getString(R.string.save)
            }
            else {
                displayTextFieldsInPresentationMode()
                binding.btnChangeViewMode.text = getString(R.string.edit)
            }
        }
    }
    private fun displayTextFieldsInPresentationMode(){
        titleValue.isEnabled = false
        amountValue.isEnabled = false
        typeValue.isEnabled = false
        categoryValue.isEnabled = false
        dateValue.isEnabled = false
        noteValue.isEnabled = false
    }
    private fun displayTextFieldsInEditMode(){
        titleValue.isEnabled = true
        amountValue.isEnabled = true
        typeValue.isEnabled = true
        categoryValue.isEnabled = true
        dateValue.isEnabled = true
        noteValue.isEnabled = true
    }

}