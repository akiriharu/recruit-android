package nz.co.test.transactions.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import nz.co.test.transactions.R
import nz.co.test.transactions.presentation.model.AmountType
import nz.co.test.transactions.presentation.model.UITransaction
import nz.co.test.transactions.presentation.theme.CombinedThemePreviews
import nz.co.test.transactions.presentation.theme.TransactionAppTheme

/**
 * Compose view to show list of transactions
 */
@Composable
internal fun TransactionsListView(
    transactionsList: List<UITransaction>,
    savedScrollPosition: Int,
    onTransactionTap: (UITransaction) -> Unit,
    onScrollPositionChanged: (Int) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val currentFirstVisibleItem = remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex }
    }

    // Update to new scroll position when it changes
    LaunchedEffect(savedScrollPosition) {
        if (savedScrollPosition >= 0) {
            lazyListState.animateScrollToItem(savedScrollPosition)
        }
    }
    // New scroll position is sae after delay
    LaunchedEffect(currentFirstVisibleItem.value) {
        delay(500)
        onScrollPositionChanged(currentFirstVisibleItem.value)
    }

    if (transactionsList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(id = R.string.no_transactions),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(transactionsList) { transaction ->
                TransactionListItemView(
                    modifier = Modifier.testTag("${TRANSACTIONS_LIST_VIEW_ITEM}${transaction.id}"),
                    transaction = transaction,
                    onTransactionTap = { onTransactionTap(transaction) },
                )
            }
        }
    }
}

@CombinedThemePreviews
@Composable
fun TransactionsListViewPreview() {
    val transaction1 = UITransaction(
        id = 1,
        summary = "Summary 1",
        transactionDate = "11/01/24 15:45",
        amount = "+$100",
        amountType = AmountType.CREDIT,
        gst = "15"
    )

    val transaction2 = UITransaction(
        id = 2,
        summary = "Summary 2",
        transactionDate = "26/10/24 05:02",
        amount = "-$3450.00",
        amountType = AmountType.DEBIT,
        gst = "234.56"
    )


    TransactionAppTheme {
        TransactionsListView(
            transactionsList = listOf(transaction1, transaction2),
            savedScrollPosition = 1,
            onTransactionTap = {},
            onScrollPositionChanged = {},
        )
    }
}

/**
 * constants for test tags
 */
const val  TRANSACTIONS_LIST_VIEW_ITEM = "transactions_list_view:transactions_list_item_"