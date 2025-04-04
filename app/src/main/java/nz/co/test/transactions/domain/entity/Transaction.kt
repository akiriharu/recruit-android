package nz.co.test.transactions.domain.entity

import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val id: Int,
    val transactionDate: LocalDateTime,
    val summary: String,
    val debit: BigDecimal,
    val credit: BigDecimal
)