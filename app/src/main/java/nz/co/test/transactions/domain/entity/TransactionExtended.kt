package nz.co.test.transactions.domain.entity

import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Data class to extend [Transaction] to add some additional fields (gst and isDebit)
 *
 * @property id The transaction id
 * @property transactionDate The date of the transaction
 * @property summary The summary of the transaction
 * @property debit The debit amount
 * @property credit The credit amount
 * @property gst The GST amount
 * @property isDebit The flag to indicate if the transaction is debit
 */
data class TransactionExtended(
    val id: Int,
    val transactionDate: LocalDateTime,
    val summary: String,
    val debit: BigDecimal,
    val credit: BigDecimal,
    val gst: BigDecimal,
    val isDebit: Boolean,
)
