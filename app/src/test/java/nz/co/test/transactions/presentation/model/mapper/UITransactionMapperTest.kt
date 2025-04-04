package nz.co.test.transactions.presentation.model.mapper

import com.google.common.truth.Truth.assertThat
import nz.co.test.transactions.domain.entity.TransactionExtended
import nz.co.test.transactions.presentation.model.AmountType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.BigDecimal
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UITransactionMapperTest {
    private var underTest = UITransactionMapper()

    @Test
    fun `test that mapper returns UITransaction object correctly with debit`() {
        // Given
        val transaction = TransactionExtended(
            id = 1,
            summary = "summary 1",
            transactionDate = LocalDateTime.of(2021, 9, 2, 10, 52),
            debit = BigDecimal(100),
            credit = BigDecimal(0),
            gst = BigDecimal(15),
            isDebit = true,
        )
        val expectedTransactionDate = "02/09/21 10:52"

        // When
        val result = underTest(transaction)

        // Then
        assertThat(result.id).isEqualTo(transaction.id)
        assertThat(result.summary).isEqualTo(transaction.summary)
        assertThat(result.transactionDate).isEqualTo(expectedTransactionDate)
        assertThat(result.amount).isEqualTo("-$100.00")
        assertThat(result.amountType).isEqualTo(AmountType.DEBIT)
        assertThat(result.gst).isEqualTo("15.00")
    }

    @Test
    fun `test that mapper returns UITransaction object correctly with credit`() {
        // Given
        val transaction = TransactionExtended(
            id = 2,
            summary = "summary 2",
            transactionDate = LocalDateTime.of(2021, 10, 3, 15, 52),
            debit = BigDecimal(0),
            credit = BigDecimal(200),
            gst = BigDecimal(15),
            isDebit = false,
        )
        val expectedTransactionDate = "03/10/21 15:52"

        // When
        val result = underTest(transaction)

        // Then
        assertThat(result.id).isEqualTo(transaction.id)
        assertThat(result.summary).isEqualTo(transaction.summary)
        assertThat(result.transactionDate).isEqualTo(expectedTransactionDate)
        assertThat(result.amount).isEqualTo("+$200.00")
        assertThat(result.amountType).isEqualTo(AmountType.CREDIT)
        assertThat(result.gst).isEqualTo("15.00")
    }

}