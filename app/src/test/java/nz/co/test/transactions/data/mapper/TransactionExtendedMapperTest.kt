package nz.co.test.transactions.data.mapper


import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import nz.co.test.transactions.data.entity.Transaction
import nz.co.test.transactions.domain.entity.TransactionExtended
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.BigDecimal
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionExtendedMapperTest {

    private lateinit var underTest: TransactionExtendedMapper

    private val transaction = Transaction(
        id = 1,
        transactionDate = LocalDateTime.of(2025, 1, 15, 14, 36),
        summary = "Summary 1",
        debit = BigDecimal.valueOf(3456),
        credit = BigDecimal.ZERO,
    )

    private val extendedTransaction = TransactionExtended(
        id = transaction.id,
        transactionDate = transaction.transactionDate,
        summary = transaction.summary,
        debit = transaction.debit,
        credit = transaction.credit,
        gst = transaction.debit.multiply(BigDecimal(0.15)),
        isDebit = true
    )

    @BeforeAll
    fun setUp() {
        underTest = TransactionExtendedMapper()
    }

    @Test
    fun `test that Transaction is mapped correctly into TransactionExtended`() = runTest {
        val result = underTest(transaction)

        assertThat(result.id).isEqualTo(extendedTransaction.id)
        assertThat(result.summary).isEqualTo(extendedTransaction.summary)
        assertThat(result.transactionDate).isEqualTo(extendedTransaction.transactionDate)
        assertThat(result.credit).isEqualTo(extendedTransaction.credit)
        assertThat(result.debit).isEqualTo(extendedTransaction.debit)
        assertThat(result.isDebit).isEqualTo(extendedTransaction.isDebit)
        assertThat(result.gst).isEqualTo(extendedTransaction.gst)
    }
}