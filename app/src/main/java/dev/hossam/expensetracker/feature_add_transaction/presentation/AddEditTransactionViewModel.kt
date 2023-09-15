package dev.hossam.expensetracker.feature_add_transaction.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hossam.expensetracker.core.android_util.Validation
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import dev.hossam.expensetracker.feature_add_transaction.domain.repository.AddTransactionRepository
import dev.hossam.expensetracker.feature_add_transaction.domain.use_case.ValidateAmountUseCase
import dev.hossam.expensetracker.feature_add_transaction.domain.use_case.ValidateCategoryUseCase
import dev.hossam.expensetracker.feature_add_transaction.domain.use_case.ValidateDateUseCase
import dev.hossam.expensetracker.feature_add_transaction.domain.use_case.ValidateTitleUseCase
import dev.hossam.expensetracker.feature_add_transaction.domain.use_case.ValidateTransactionTypeUseCase
import dev.hossam.expensetracker.feature_detail.presentation.FormEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
    private val repo: AddTransactionRepository,
    private val savedStateHandle: SavedStateHandle,
    private val titleUseCase: ValidateTitleUseCase,
    private val amountUseCase: ValidateAmountUseCase,
    private val typeUseCase: ValidateTransactionTypeUseCase,
    private val categoryUseCase: ValidateCategoryUseCase,
    private val dateUseCase: ValidateDateUseCase,
) : ViewModel() {

    private var id: Int? = null

    private var _titleValidation = MutableStateFlow(Validation())
    val titleValidation = _titleValidation.asStateFlow()

    private var _amountValidation = MutableStateFlow(Validation())
    val amountValidation = _amountValidation.asStateFlow()

    private var _typeValidation = MutableStateFlow(Validation())
    val typeValidation = _typeValidation.asStateFlow()

    private var _categoryValidation = MutableStateFlow(Validation())
    val categoryValidation = _categoryValidation.asStateFlow()

    private var _dateValidation = MutableStateFlow(Validation())
    val dateValidation = _dateValidation.asStateFlow()

    private var _clearInputs = MutableSharedFlow<Boolean>()
    val clearInputs = _clearInputs.asSharedFlow()

    private var note: String? = null

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            this.id = id
        }
    }

    fun onEvent(formEvent: FormEvent) {
        when (formEvent) {
            is FormEvent.Title -> {
                val validationResult = titleUseCase(formEvent.value)
                _titleValidation.value = titleValidation.value.copy(
                    isValid = validationResult.isValid,
                    errorMessage = validationResult.errorMessage,
                    value = formEvent.value
                )
            }

            is FormEvent.Amount -> {
                val validationResult = amountUseCase(formEvent.value)
                _amountValidation.value = amountValidation.value.copy(
                    isValid = validationResult.isValid,
                    errorMessage = validationResult.errorMessage,
                    value = formEvent.value
                )
            }

            is FormEvent.TransactionType -> {
                val validationResult = typeUseCase(formEvent.value)
                _typeValidation.value = typeValidation.value.copy(
                    isValid = validationResult.isValid,
                    errorMessage = validationResult.errorMessage,
                    value = formEvent.value
                )
            }

            is FormEvent.Category -> {
                val validationResult = categoryUseCase(formEvent.value)
                _categoryValidation.value = categoryValidation.value.copy(
                    isValid = validationResult.isValid,
                    errorMessage = validationResult.errorMessage,
                    value = formEvent.value
                )
            }

            is FormEvent.Date -> {
                val validationResult = dateUseCase(formEvent.value.toString())
                _dateValidation.value = dateValidation.value.copy(
                    isValid = validationResult.isValid,
                    errorMessage = validationResult.errorMessage,
                    value = formEvent.value.toString()
                )
            }

            is FormEvent.Note -> {
                note = formEvent.value
            }

            FormEvent.Save -> {
                submitUpdates()
            }

        }
    }

    private fun submitUpdates() = viewModelScope.launch(Dispatchers.IO) {
        val isValidTitle = titleValidation.value.isValid
        val isValidAmount = amountValidation.value.isValid
        val isValidType = typeValidation.value.isValid
        val isValidCategory = categoryValidation.value.isValid
        val hasDate = dateValidation.value.isValid
        if (isValidTitle == true
            && isValidAmount == true
            && isValidType == true
            && isValidCategory == true
            && hasDate == true
        ) {
            val transactionDto = TransactionDTO(
                id = id,
                title = titleValidation.value.value,
                amount = amountValidation.value.value.toDouble(),
                type = typeValidation.value.value,
                category = categoryValidation.value.value,
                date = dateValidation.value.value.toLong(),
                note = note,
            )
            repo.addTransaction(transactionDto)
            _clearInputs.emit(true)
            return@launch
        }
        return@launch
    }

}