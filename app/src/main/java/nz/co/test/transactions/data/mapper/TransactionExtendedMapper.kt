package nz.co.test.transactions.data.mapper

import nz.co.test.transactions.data.entity.Transaction
import nz.co.test.transactions.domain.entity.TransactionExtended
import java.math.BigDecimal
import javax.inject.Inject

class TransactionExtendedMapper @Inject constructor() {

    internal operator fun invoke(transaction: Transaction): TransactionExtended = TransactionExtended(
        id = transaction.id,
        transactionDate = transaction.transactionDate,
        summary = transaction.summary,
        debit = transaction.debit,
        credit = transaction.credit,
        gst = calculateGst(transaction),
        isDebit = transaction.debit.signum() != 0
    )

    private fun calculateGst(transaction: Transaction): BigDecimal =
        if (transaction.credit.signum() == 0)
            transaction.debit.multiply(BigDecimal(GST))
        else
            transaction.credit.multiply(BigDecimal(GST))

    companion object {
        private const val GST = 0.15
    }
}
