package dev.hossam.expensetracker.feature_detail.presentation

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.expensetracker.R
import dev.hossam.expensetracker.databinding.FragmentDetailBinding
import dev.hossam.expensetracker.feature_add_transaction.presentation.AddEditTransactionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val getDetailViewModel: GetDetailViewModel by viewModels()
    private val addEditTransactionViewModel: AddEditTransactionViewModel by viewModels()
    private val titleSubject by lazy { binding.layoutMemberTitle.tvSubject }
    private val titleValue by lazy { binding.layoutMemberTitle.tvSubjectValue }
    private val amountSubject by lazy { binding.layoutMemberAmount.tvSubject }
    private val amountValue by lazy { binding.layoutMemberAmount.tvSubjectValue }
    private val dropMenuTransType by lazy { binding.transactionTypeMenu }
    private val dropMenuTransCategory by lazy { binding.transactionCategoryMenu }
    private val dateSubject by lazy { binding.layoutMemberDate.tvSubject }
    private val dateValue by lazy { binding.layoutMemberDate.tvSubjectValue }
    private val noteSubject by lazy { binding.layoutMemberNote.tvSubject }
    private val noteValue by lazy { binding.layoutMemberNote.tvSubjectValue }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        changeAmountFieldKeyboardInputType()
        disableDateFromEditing()
        setupSubjectsStringsFromResources()
        setupTransactionTypesDropMenuAdapter()
        setupCategoriesDropMenuAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeFieldsInteractionType()
        collectUiState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupTransactionTypesDropMenuAdapter() {
        val transTypes = resources.getStringArray(R.array.transaction_types)
        val transArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, transTypes)
        dropMenuTransType.setAdapter(transArrayAdapter)
    }

    private fun setupCategoriesDropMenuAdapter() {
        val transCategories = resources.getStringArray(R.array.categories)
        val transArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, transCategories)
        binding.transactionCategoryMenu.setAdapter(transArrayAdapter)
    }
    private fun setupSubjectsStringsFromResources() {
        titleSubject.text = getString(R.string.title)
        amountSubject.text = getString(R.string.amount)
        dateSubject.text = getString(R.string.date)
        noteSubject.text = getString(R.string.note)
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                getDetailViewModel.state.collectLatest {
                    withContext(Dispatchers.Main) {
                        bindDataToUI(detailState = it)
                    }
                }
            }
        }
    }

    private fun bindDataToUI(detailState: TransactionDetailState?) {
        detailState?.let { transDetailState ->
            transDetailState.apply {
                titleValue.setText(title)
                amountValue.setText("$amount")
                dropMenuTransType.setText(type)
                dropMenuTransCategory.setText(category)
                dateValue.setText(date)
                noteValue.setText(note)
            }
        }
    }

    private fun changeFieldsInteractionType() {
        binding.btnChangeViewMode.setOnClickListener {
            sendLatestValues()
            collectFieldsErrors()
        }
    }

    private fun disableDateFromEditing(){
        dateValue.isEnabled = false
    }

    private fun sendLatestValues() {
        addEditTransactionViewModel.onEvent(FormEvent.Title(titleValue.text.toString()))
        addEditTransactionViewModel.onEvent(FormEvent.Amount(amountValue.text.toString()))

    }

    private fun openDatePickerDialog(){
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(10)
            .setMinute(0)
            .setTitleText("Choose Time")
            .build()
        picker.showsDialog
    }
    private fun dateListener(){}

    private fun collectFieldsErrors() {
        combine(
            addEditTransactionViewModel.titleValidation,
            addEditTransactionViewModel.amountValidation,
            addEditTransactionViewModel.typeValidation,
            addEditTransactionViewModel.categoryValidation,
        ) { title, amount, type, category ->
            titleValue.error = title.errorMessage
            amountValue.error = amount.errorMessage
            binding.layoutMemberType.error = type.errorMessage
            binding.layoutMemberCategory.error = category.errorMessage
        }.launchIn(lifecycleScope)
    }

    private fun changeAmountFieldKeyboardInputType(){
        amountValue.inputType = InputType.TYPE_CLASS_NUMBER
    }

}