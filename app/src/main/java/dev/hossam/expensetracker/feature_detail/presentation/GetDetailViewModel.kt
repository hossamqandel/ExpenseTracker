package dev.hossam.expensetracker.feature_detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hossam.expensetracker.feature_detail.domain.repository.TransactionDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetDetailViewModel @Inject constructor(
    private val repo: TransactionDetailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionDetailState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            submitDetailData(id = id)
        }

    }

    private fun submitDetailData(id: Int) = viewModelScope.launch {
        val trans = repo.getTransactionDetail(id = id)
        trans?.apply {
            _state.value = state.value.copy(
                id = id,
                title = title,
                amount = amount,
                type = type,
                category = category,
                date = date,
                note = note
            )
        }
    }
}