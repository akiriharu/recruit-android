package nz.co.test.transactions.presentation.model


/**
 * The UI state for the list of transactions
 *
 * @property transactionsList The list of transactions
 * @property selectedTransaction the transaction, which is selected when user tap on it in the list
 * @property savedScrollPosition saved position of the list, when user scrolls
 */
data class TransactionsUIState(
    val transactionsList: List<UITransaction> = emptyList(),
    val selectedTransaction: UITransaction? = null,
    val savedScrollPosition: Int = -1
)
