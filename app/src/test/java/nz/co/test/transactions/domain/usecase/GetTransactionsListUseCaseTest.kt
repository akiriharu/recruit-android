package nz.co.test.transactions.domain.usecase

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
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

    private val extendedTransaction1 = TransactionExtended(
        id = 1,
        transactionDate = LocalDateTime.of(2025, 1, 15, 14, 36),
        summary = "Summary 1",
        debit = BigDecimal.valueOf(3456),
        credit = BigDecimal.ZERO,
        gst = BigDecimal.valueOf(3456).multiply(BigDecimal(0.15)),
        isDebit = true
    )

    private val extendedTransaction2 = TransactionExtended(
        id = 2,
        transactionDate = LocalDateTime.of(2024, 7, 29, 5, 16),
        summary = "Summary 2",
        debit = BigDecimal.ZERO,
        credit = BigDecimal.valueOf(2345.6),
        gst = BigDecimal.valueOf(2345.6).multiply(BigDecimal(0.15)),
        isDebit = false
    )

    private val initialTransactionsList = listOf(extendedTransaction2, extendedTransaction1)
    private val expectedTransactionsList = listOf(extendedTransaction1, extendedTransaction2)

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
        whenever(transactionsRepository.fetchTransactions()).thenReturn(initialTransactionsList)

        assertThat(underTest()).isEqualTo(expectedTransactionsList)
    }
}