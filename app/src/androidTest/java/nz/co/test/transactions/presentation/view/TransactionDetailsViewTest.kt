package nz.co.test.transactions.presentation.view

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import nz.co.test.transactions.presentation.model.AmountType
import nz.co.test.transactions.presentation.model.UITransaction
import org.junit.Rule
import org.junit.Test


class TransactionDetailsViewTest {

    @get:Rule
    val composeRule = createComposeRule()

    private fun setContent() {
        composeRule.setContent {
            TransactionDetailsView(
                transaction = UITransaction(
                    id = 1,
                    summary = "Summary 1",
                    transactionDate = "11/01/24 15:45",
                    amount = "+$100",
                    amountType = AmountType.CREDIT,
                    gst = "15"
                )
            )
        }
    }

    @Test
    fun testThatTransactionsDetailsAreDisplayed() {
        setContent()

        composeRule.onNodeWithTag(TRANSACTION_DETAILS_VIEW_SUMMARY).assertExists()
        composeRule.onNodeWithTag(TRANSACTION_DETAILS_VIEW_AMOUNT).assertExists()
        composeRule.onNodeWithTag(TRANSACTION_DETAILS_VIEW_TRANSACTION_DATE).assertExists()
        composeRule.onNodeWithTag(TRANSACTION_DETAILS_VIEW_GST).assertExists()
    }

    @Test
    fun testThatTransactionSummaryIsShownCorrectly() {
        val expectedSummary = "Summary 1"
        setContent()

        composeRule.onNodeWithTag(TRANSACTION_DETAILS_VIEW_SUMMARY, useUnmergedTree = true)
            .assertTextEquals(expectedSummary)
    }

    @Test
    fun testThatAmountIsShownCorrectly() {
        val expectedAmount = "+$100"
        setContent()

        composeRule.onNodeWithTag(TRANSACTION_DETAILS_VIEW_AMOUNT, useUnmergedTree = true)
            .assertTextEquals(expectedAmount)
    }

    @Test
    fun testThatTransactionDateIsShownCorrectly() {
        val expectedDate = "Date: 11/01/24 15:45"
        setContent()

        composeRule.onNodeWithTag(TRANSACTION_DETAILS_VIEW_TRANSACTION_DATE, useUnmergedTree = true)
            .assertTextEquals(expectedDate)
    }

    @Test
    fun testThatGstIsShownCorrectly() {
        val expectedGST = "GST: 15"
        setContent()

        composeRule.onNodeWithTag(TRANSACTION_DETAILS_VIEW_GST, useUnmergedTree = true)
            .assertTextEquals(expectedGST)
    }
}