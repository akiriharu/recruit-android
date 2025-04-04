package nz.co.test.transactions.presentation.model

import nz.co.test.transactions.domain.entity.TransactionExtended

/**
 * The UI state for the list of transactions
 *
 * @property transactionsList The list of transactions
 */
data class TransactionsUIState(
    val transactionsList: List<UITransaction> = emptyList(),
)
