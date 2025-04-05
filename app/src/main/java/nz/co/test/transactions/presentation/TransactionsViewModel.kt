package nz.co.test.transactions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nz.co.test.transactions.domain.usecase.GetTransactionsListUseCase
import nz.co.test.transactions.presentation.model.TransactionsUIState
import nz.co.test.transactions.presentation.model.UITransaction
import nz.co.test.transactions.presentation.model.mapper.UITransactionMapper
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
    private val getTransactionsListUseCase: GetTransactionsListUseCase,
    private val uiTransactionMapper: UITransactionMapper,
) : ViewModel() {
    private val _state = MutableStateFlow(TransactionsUIState())
    val state: StateFlow<TransactionsUIState> = _state

    init {
        viewModelScope.launch {
            runCatching {
                getTransactionsListUseCase()
            }.onSuccess { transactionsList ->
                val uiTransactionList = transactionsList.map { transactionExtended ->
                    uiTransactionMapper(transactionExtended)
                }
                _state.update { it.copy(transactionsList = uiTransactionList) }
            }.onFailure {
                Timber.e("Failed to fetch list of transactions: ${it.message}")
            }
        }
    }

    fun onTransactionSelected(transaction: UITransaction) {
        _state.update { it.copy(selectedTransaction = transaction) }
    }
}