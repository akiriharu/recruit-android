package nz.co.test.transactions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nz.co.test.transactions.domain.usecase.GetTransactionsListUseCase
import timber.log.Timber
import javax.inject.Inject

/**
 * View model to handle Transactions-related operations
 *
 * @param getTransactionsListUseCase use case to fetch list of transactions (extended)
 *
 * @property state Current UI state
 */
@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactionsListUseCase: GetTransactionsListUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TransactionsUIState())
    val state: StateFlow<TransactionsUIState> = _state

    init {
        viewModelScope.launch {
            runCatching {
                getTransactionsListUseCase()
            }.onSuccess { transactionsList ->
                _state.update { it.copy(transactionsList = transactionsList) }
            }.onFailure {
                Timber.e("Failed to fetch list of transactions: ${it.message}")
            }
        }
    }
}