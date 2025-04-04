package nz.co.test.transactions.presentation.view

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nz.co.test.transactions.presentation.model.AmountType
import nz.co.test.transactions.presentation.model.UITransaction
import org.junit.Rule
import org.junit.Test


class TransactionsListViewTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val transaction1 = UITransaction(
        id = 1,
        summary = "Summary 1",
        transactionDate = "11/01/24 15:45",
        amount = "+$100",
        amountType = AmountType.CREDIT,
        gst = "15"
    )
    private val transaction2 = transaction1.copy(id = 2)
    private val transaction3 = transaction1.copy(id = 3)

    @Test
    fun testThatListItemsAreShown() {
        composeRule.setContent {
            TransactionsListView(
                transactionsList = listOf(transaction1, transaction2, transaction3),
                onTransactionTap = {}
            )
        }

        composeRule.onNodeWithTag("${TRANSACTIONS_LIST_VIEW_ITEM}1", useUnmergedTree = true)
            .assertExists()
        composeRule.onNodeWithTag("${TRANSACTIONS_LIST_VIEW_ITEM}2", useUnmergedTree = true)
            .assertExists()
        composeRule.onNodeWithTag("${TRANSACTIONS_LIST_VIEW_ITEM}3", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun testThatTappingTransactionInvokesClickCallback() {
        val tapped = mutableStateOf(false)
        composeRule.setContent {
            TransactionsListView(
                transactionsList = listOf(transaction1, transaction2, transaction3),
                onTransactionTap = { tapped.value = true }
            )
        }

        composeRule.onNodeWithTag("${TRANSACTIONS_LIST_VIEW_ITEM}1", useUnmergedTree = true)
            .performClick()

        assert(tapped.value)
    }
}