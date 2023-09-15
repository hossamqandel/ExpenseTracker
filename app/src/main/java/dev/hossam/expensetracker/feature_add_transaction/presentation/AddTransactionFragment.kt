package dev.hossam.expensetracker.feature_add_transaction.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import dev.hossam.expensetracker.R
import dev.hossam.expensetracker.databinding.FragmentAddTransactionBinding
import dev.hossam.expensetracker.feature_detail.presentation.FormEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE
import java.util.Calendar.MONTH
import java.util.Calendar.YEAR
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddTransactionFragment : Fragment() {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddEditTransactionViewModel by viewModels()
    private var timePicker: MaterialTimePicker? = null
    private var calendar: Calendar? = null
    private var simpleDateFormat: SimpleDateFormat? = null
    private val screenTopTitle by lazy { binding.layputBackarrowWithScreenName.tvScreenName }
    private val btnBack by lazy { binding.layputBackarrowWithScreenName.ivBtnBack }
    private val labelTitle by lazy { binding.layoutTitle.layoutInput }
    private val etTitle by lazy { binding.layoutTitle.etInput }
    private val labelAmount by lazy { binding.layoutAmount.layoutInput }
    private val etAmount by lazy { binding.layoutAmount.etInput }
    private val labelDropMenuType by lazy { binding.layoutMenuTransType }
    private val dropMenuTypeValue by lazy { binding.dropMenuType }
    private val labelDropMenuCategory by lazy { binding.layoutMenuCategory }
    private val dropMenuCategoryValue by lazy { binding.dropMenuCategory }
    private val labelDate by lazy { binding.layoutDate }
    private val tvDate by lazy { binding.etDate }
    private val labelNote by lazy { binding.layoutNote.layoutInput }
    private val etNote by lazy { binding.layoutNote.etInput }

    private fun initProperties() {
        simpleDateFormat = SimpleDateFormat(
            "EEEE, d MMM h:mm a",
            Locale.getDefault()
        )
    }

    private fun setupTransactionTypesDropMenuAdapter() {
        val transTypes = resources.getStringArray(R.array.transaction_types)
        val transArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, transTypes)
        binding.dropMenuType.setAdapter(transArrayAdapter)
    }

    private fun setupCategoriesDropMenuAdapter() {
        val transCategories = resources.getStringArray(R.array.categories)
        val transArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, transCategories)
        binding.dropMenuCategory.setAdapter(transArrayAdapter)
    }

    private fun setupSubjectsStringsFromResources() {
        labelTitle.hint = getString(R.string.title)
        labelAmount.hint = getString(R.string.amount)
        labelDropMenuType.hint = getString(R.string.transaction_type)
        labelDropMenuCategory.hint = getString(R.string.category)
        labelDate.hint = getString(R.string.date)
        labelNote.hint = getString(R.string.note)
        screenTopTitle.text = getString(R.string.add_transaction)
    }

    private fun changeAmountFieldKeyboardInputType() {
        etAmount.inputType = TYPE_CLASS_NUMBER or TYPE_NUMBER_FLAG_DECIMAL
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        initProperties()
        setupSubjectsStringsFromResources()
        setupTransactionTypesDropMenuAdapter()
        setupCategoriesDropMenuAdapter()
//        disableDateFromEditing()
        changeAmountFieldKeyboardInputType()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClicks()
        collectFieldsErrors()
        clearAllWhenTransactionAddedSuccessfully()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        timePicker = null
        calendar = null
        simpleDateFormat = null
    }

    private fun onClicks() {
        binding.apply {

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnAddTransaction.setOnClickListener {
                sendUiFieldsData()
            }

            etDate.setOnClickListener {
                showDatePicker()
            }
        }
    }


    private fun sendUiFieldsData() {
        viewModel.onEvent(FormEvent.Title(etTitle.text.toString()))
        viewModel.onEvent(FormEvent.Amount(etAmount.text.toString()))
        viewModel.onEvent(FormEvent.TransactionType(dropMenuTypeValue.text.toString()))
        viewModel.onEvent(FormEvent.Category(dropMenuCategoryValue.text.toString()))
        viewModel.onEvent(FormEvent.Note(etNote.text.toString()))
        viewModel.onEvent(FormEvent.Save)

        if (tvDate.text.toString().isBlank()) {
            viewModel.onEvent(FormEvent.Date(null))
        } else {
            val dateTimeInMillis = getDateAndTimeInMillis()
            viewModel.onEvent(FormEvent.Date(dateTimeInMillis))
        }
    }

    private fun collectFieldsErrors() {
        combine(
            viewModel.titleValidation,
            viewModel.amountValidation,
            viewModel.typeValidation,
            viewModel.categoryValidation,
            viewModel.dateValidation,
        ) { title, amount, type, category, date ->
            labelTitle.error = title.errorMessage
            labelAmount.error = amount.errorMessage
            labelDate.error = date.errorMessage
            binding.layoutMenuTransType.error = type.errorMessage
            binding.layoutMenuCategory.error = category.errorMessage
        }.launchIn(lifecycleScope)
    }

    private fun clearAllWhenTransactionAddedSuccessfully() {
        lifecycleScope.launch {
            viewModel.clearInputs.collectLatest { shouldClear ->
                if (shouldClear) {
                    clearFieldsInputs()
                    Snackbar.make(
                        binding.root,
                        getText(R.string.transaction_has_been_added_successfully),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun clearFieldsInputs() {
        etTitle.setText("")
        etAmount.setText("")
        tvDate.setText("")
        etNote.setText("")
    }

    private fun showDatePicker() {
        calendar = null
        calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            calendar?.set(year, month, day)
            calendar?.set(YEAR, year)
            calendar?.set(MONTH, month)
            calendar?.set(DAY_OF_MONTH, day)
            showTimePicker()
        }

        calendar?.let { safeCalendar ->
            DatePickerDialog(
                requireContext(), datePicker,
                safeCalendar.get(YEAR),
                safeCalendar.get(MONTH),
                safeCalendar.get(DAY_OF_MONTH)
            ).show()
        }
    }

    private fun showTimePicker() {
        timePicker = null
        val currentMillis = System.currentTimeMillis()
        val currentDeviceHour = ((currentMillis / (1000 * 60 * 60)) % 24).toInt()
        val currentDeviceMinute = ((currentMillis / (1000 * 60)) % 60).toInt()
        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(currentDeviceHour)
            .setMinute(currentDeviceMinute)
            .setTitleText(getString(R.string.set_time))
            .build()
        timePicker?.show(childFragmentManager, null)
        timeDialogListener()
    }

    private fun timeDialogListener() {
        timePicker?.addOnPositiveButtonClickListener {
            setFormattedDateTimeOnUi()
        }
        timePicker?.addOnNegativeButtonClickListener { cancelPickedDate() }
        timePicker?.addOnCancelListener { cancelPickedDate() }
        timePicker?.addOnDismissListener { }
    }

    /**
     * @see this function is to remove the selected date from date picker
     * @see because user may select the date without picking up the time
     * @see and without this step, viewmodel will read the date as a
     * @see non blank value and the edittext will not give red flag error
     * @see even if is blank
     * */
    private fun cancelPickedDate() {
        calendar = null
    }

    private fun setFormattedDateTimeOnUi() {
        val dateTimeInMillis = getDateAndTimeInMillis()
        val date = Date(dateTimeInMillis ?: System.currentTimeMillis())
        val formattedDate = simpleDateFormat?.format(date)
        tvDate.setText(formattedDate)
    }

    private fun getDateAndTimeInMillis(): Long? {
        val hour = timePicker?.hour
        val minute = timePicker?.minute
        if (calendar != null
            && hour != null
            && minute != null
        ) {
            calendar?.set(HOUR_OF_DAY, hour)
            calendar?.set(MINUTE, minute)
            return calendar?.timeInMillis
        }
        return null
    }
}