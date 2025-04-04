package nz.co.test.transactions.presentation.view

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nz.co.test.transactions.presentation.model.AmountType
import nz.co.test.transactions.presentation.model.UITransaction
import org.junit.Rule
import org.junit.Test

class TransactionListItemViewTest {

    @get:Rule
    val composeRule = createComposeRule()

    private fun setContent(
        onTransactionTap: () -> Unit = {},
    ) = composeRule.setContent {
        TransactionListItemView(
            transaction = UITransaction(
                id = 1,
                summary = "Summary 1",
                transactionDate = "11/01/25 15:45",
                amount = "+$100",
                amountType = AmountType.CREDIT,
                gst = "15"
            ),
            onTransactionTap = { onTransactionTap() },
        )
    }

    @Test
    fun testThatTransactionSummaryIsShown() {
        val expectedSummary = "Summary 1"
        setContent()

        composeRule.onNodeWithTag(TRANSACTIONS_LIST_ITEM_VIEW_SUMMARY, useUnmergedTree = true)
            .assertTextEquals(expectedSummary)
    }

    @Test
    fun testThatAmountIsShown() {
        val expectedAmount = "+$100"
        setContent()

        composeRule.onNodeWithTag(TRANSACTIONS_LIST_ITEM_VIEW_AMOUNT, useUnmergedTree = true)
            .assertTextEquals(expectedAmount)
    }

    @Test
    fun testThatTransactionDateIsShown() {
        val expectedDate = "Date: 11/01/25 15:45"
        setContent()

        composeRule.onNodeWithTag(TRANSACTIONS_LIST_ITEM_VIEW_TRANSACTION_DATE, useUnmergedTree = true)
            .assertTextEquals(expectedDate)
    }

    @Test
    fun testThatTappingCardInvokesClickCallback() {
        val tapped = mutableStateOf(false)
        setContent(onTransactionTap = { tapped.value = true })

        composeRule.onNodeWithTag(TRANSACTIONS_LIST_ITEM_VIEW_CARD, useUnmergedTree = true)
            .performClick()
        assert(tapped.value)
    }
}