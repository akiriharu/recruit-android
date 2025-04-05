package nz.co.test.transactions.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nz.co.test.transactions.R
import nz.co.test.transactions.presentation.model.AmountType
import nz.co.test.transactions.presentation.model.UITransaction
import nz.co.test.transactions.presentation.theme.CombinedThemePreviews
import nz.co.test.transactions.presentation.theme.LocalCustomColours
import nz.co.test.transactions.presentation.theme.TransactionAppTheme
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * Compose view to display details of individual transaction
 */
@Composable
fun TransactionDetailsView(
    transaction: UITransaction,
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
    ) {
        Column(modifier = Modifier.padding(start = 18.dp, end = 18.dp)) {
            Text(
                text = transaction.summary,
                modifier = Modifier
                    .padding(8.dp)
                    .testTag(TRANSACTION_DETAILS_VIEW_SUMMARY),
                style = MaterialTheme.typography.h6
            )
            Row(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier.weight(0.8f)) {
                    Text(
                        text = transaction.amount,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .testTag(TRANSACTION_DETAILS_VIEW_AMOUNT),
                        color = defineAmountColour(transaction.amountType),
                        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                    )
                }
                Column {
                    Text(
                        text = stringResource(R.string.transaction_gst, transaction.gst),
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .testTag(TRANSACTION_DETAILS_VIEW_GST),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            Text(
                text = stringResource(R.string.transaction_date, transaction.transactionDate),
                modifier = Modifier
                    .padding(8.dp)
                    .testTag(TRANSACTION_DETAILS_VIEW_TRANSACTION_DATE),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

/**
 * Function to define colour for each type of transaction
 */
@Composable
private fun defineAmountColour(type: AmountType): Color = when (type) {
    AmountType.CREDIT -> LocalCustomColours.current.customCreditColor
    AmountType.DEBIT -> LocalCustomColours.current.customDebitColor
}

@CombinedThemePreviews
@Composable
fun TransactionDetailsViewPreview() {
    TransactionAppTheme {
        TransactionDetailsView(
            transaction = UITransaction(
                id = 1,
                summary = "Transaction summary",
                transactionDate = OffsetDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")),
                amount = "+$${BigDecimal(100)}",
                amountType = AmountType.CREDIT,
                gst = BigDecimal.ONE.toString(),
            )
        )
    }
}

/**
 * constants for test tags
 */
const val TRANSACTION_DETAILS_VIEW_SUMMARY = "transaction_details_view:text_summary"
const val TRANSACTION_DETAILS_VIEW_AMOUNT = "transaction_details_view:text_amount"
const val TRANSACTION_DETAILS_VIEW_TRANSACTION_DATE =
    "transaction_details_view:text_transaction_date"
const val TRANSACTION_DETAILS_VIEW_GST = "transaction_details_view:text_gst"