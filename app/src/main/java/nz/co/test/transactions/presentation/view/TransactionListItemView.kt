package nz.co.test.transactions.presentation.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun TransactionListItemView(
    modifier: Modifier = Modifier,
    transaction: UITransaction,
    onTransactionTap: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                onClick = { onTransactionTap() }
            )
            .testTag(TRANSACTIONS_LIST_ITEM_VIEW_CARD)
    ) {
        Column {
            Row {
                Text(
                    text = transaction.summary,
                    modifier = Modifier
                        .padding(8.dp)
                        .testTag(TRANSACTIONS_LIST_ITEM_VIEW_SUMMARY),
                    style = MaterialTheme.typography.h6
                )
            }
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(
                            id = R.string.transaction_date,
                            transaction.transactionDate
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .testTag(TRANSACTIONS_LIST_ITEM_VIEW_TRANSACTION_DATE),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                Column {
                    Text(
                        text = transaction.amount,
                        modifier = Modifier
                            .padding(8.dp)
                            .testTag(TRANSACTIONS_LIST_ITEM_VIEW_AMOUNT),
                        color = defineAmountColour(transaction.amountType),
                        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@Composable
private fun defineAmountColour(type: AmountType): Color = when(type) {
    AmountType.CREDIT -> LocalCustomColours.current.customCreditColor
    AmountType.DEBIT -> LocalCustomColours.current.customDebitColor
}

@CombinedThemePreviews
@Composable
fun TransactionListItemViewPreview() {
    TransactionAppTheme {
        TransactionListItemView(
            transaction = UITransaction(
                id = 1,
                summary = "Transaction summary",
                transactionDate = OffsetDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")),
                amount = "+$${ BigDecimal(100) }",
                amountType = AmountType.CREDIT,
                gst = BigDecimal.ONE.toString(),
            ),
            onTransactionTap = {}
        )
    }
}


const val TRANSACTIONS_LIST_ITEM_VIEW_CARD = "transactions_list_item_view:card"
const val TRANSACTIONS_LIST_ITEM_VIEW_SUMMARY = "transactions_list_item_view:text_summary"
const val TRANSACTIONS_LIST_ITEM_VIEW_TRANSACTION_DATE = "transactions_list_item_view:text_transaction_date"
const val TRANSACTIONS_LIST_ITEM_VIEW_AMOUNT = "transactions_list_item_view:text_amount"