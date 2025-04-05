package nz.co.test.transactions.presentation.model.mapper

import nz.co.test.transactions.domain.entity.TransactionExtended
import nz.co.test.transactions.presentation.model.AmountType
import nz.co.test.transactions.presentation.model.UITransaction
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * To map the TransactionExtended to UITransaction
 */
class UITransactionMapper @Inject constructor() {
    /**
     * Invoke
     * Convert the TransactionExtended to UITransaction
     * to prepare the data class being consumed by UI
     *
     * @param transaction The extended transaction to map
     * @return  [UITransaction]
     */
    internal operator fun invoke(
        transaction: TransactionExtended
    ): UITransaction {
        val formatter =  DecimalFormat("#,##0.00")
        return UITransaction(
            id = transaction.id,
            summary = transaction.summary,
            transactionDate = transaction.transactionDate.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")),
            amount =
            if (transaction.isDebit)
                "-$${formatter.format(transaction.debit)}"
            else
                "+$${formatter.format(transaction.credit)}",
            amountType = if (transaction.isDebit) AmountType.DEBIT else AmountType.CREDIT,
            gst = formatter.format(transaction.gst),
        )
    }
}