package nz.co.test.transactions.data.entity

import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Data class to describe received JSON data for Transaction
 */
data class Transaction(
    val id: Int,
    val transactionDate: LocalDateTime,
    val summary: String,
    val debit: BigDecimal,
    val credit: BigDecimal
)