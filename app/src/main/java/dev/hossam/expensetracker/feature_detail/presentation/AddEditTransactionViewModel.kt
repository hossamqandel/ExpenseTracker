package dev.hossam.expensetracker.feature_detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hossam.expensetracker.core.android_util.Validate
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import dev.hossam.expensetracker.feature_add_transaction.domain.repository.AddTransactionRepository
import dev.hossam.expensetracker.feature_detail.domain.use_case.ValidateAmountUseCase
import dev.hossam.expensetracker.feature_detail.domain.use_case.ValidateCategoryUseCase
import dev.hossam.expensetracker.feature_detail.domain.use_case.ValidateTitleUseCase
import dev.hossam.expensetracker.feature_detail.domain.use_case.ValidateTransactionTypeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
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
) : ViewModel() {

    private var id: Int = -1

    private var _titleValidation = MutableStateFlow(Validate())
    val titleValidation = _titleValidation.asStateFlow()

    private var _amountValidation = MutableStateFlow(Validate())
    val amountValidation = _amountValidation.asStateFlow()

    private var _typeValidation = MutableStateFlow(Validate())
    val typeValidation = _typeValidation.asStateFlow()

    private var _categoryValidation = MutableStateFlow(Validate())
    val categoryValidation = _categoryValidation.asStateFlow()

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            this.id = id
        }
    }
    var isPresentationMode: Boolean = false
        get() = field
        set(value) {
            field = value
        }

    fun validateFields(formEvent: FormEvent) {
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

            FormEvent.Save -> {
                submitUpdates()
            }
        }
    }

    private fun submitUpdates() = viewModelScope.launch(Dispatchers.IO){
        val isValidTitle = titleValidation.value.isValid
        val isValidAmount = amountValidation.value.isValid
        val isValidType = typeValidation.value.isValid
        val isValidCategory = categoryValidation.value.isValid
        if (isValidTitle == true
            && isValidAmount  == true
            && isValidType == true
            && isValidCategory == true
            ){

            val transactionDto = TransactionDTO(
                id = id,
                title = titleValidation.value.value,
                amount = amountValidation.value.value.toDouble(),
                type = typeValidation.value.value,
                category = categoryValidation.value.value,
                date = System.currentTimeMillis().toString(),
                note = null,
            )
            repo.addTransaction(transactionDto)
            return@launch
        }
        return@launch
    }

}