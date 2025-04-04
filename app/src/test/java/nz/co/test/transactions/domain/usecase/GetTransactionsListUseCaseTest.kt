package nz.co.test.transactions.domain.usecase

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import nz.co.test.transactions.domain.entity.Transaction
import nz.co.test.transactions.domain.entity.TransactionExtended
import nz.co.test.transactions.domain.repository.TransactionsRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDateTime


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetTransactionsListUseCaseTest {
    private lateinit var underTest: GetTransactionsListUseCase

    private val transactionsRepository = mock<TransactionsRepository>()

    private val transaction1 = Transaction(
        id = 1,
        transactionDate = LocalDateTime.of(2025, 1, 15, 14, 36),
        summary = "Summary 1",
        debit = BigDecimal.valueOf(3456),
        credit = BigDecimal.ZERO,
    )

    private val transaction2 = Transaction(
        id = 2,
        transactionDate = LocalDateTime.of(2024, 7, 29, 5, 16),
        summary = "Summary 2",
        debit = BigDecimal.ZERO,
        credit = BigDecimal.valueOf(2345.6),
    )

    private val transactionsList = listOf(transaction1, transaction2)

    private val extendedTransaction1 = TransactionExtended(
        id = transaction1.id,
        transactionDate = transaction1.transactionDate,
        summary = transaction1.summary,
        debit = transaction1.debit,
        credit = transaction1.credit,
        gst = transaction1.debit.multiply(BigDecimal(0.15)),
        isDebit = true
    )

    private val extendedTransaction2 = TransactionExtended(
        id = transaction2.id,
        transactionDate = transaction2.transactionDate,
        summary = transaction2.summary,
        debit = transaction2.debit,
        credit = transaction2.credit,
        gst = transaction2.credit.multiply(BigDecimal(0.15)),
        isDebit = false
    )

    private val extendedTransactionsList = listOf(extendedTransaction1, extendedTransaction2)

    @BeforeAll
    fun setUp() {
        underTest = GetTransactionsListUseCase(transactionsRepository)
    }

    @BeforeEach
    fun resetMocks() {
        reset(transactionsRepository)
    }

    @Test
    fun `test that list of extended and sorted transactions is fetched`() = runTest {
        whenever(transactionsRepository.fetchTransactions()).thenReturn(transactionsList)

        assertThat(underTest()).isEqualTo(extendedTransactionsList)
    }
}