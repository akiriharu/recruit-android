package nz.co.test.transactions.data.repository

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import nz.co.test.transactions.data.services.TransactionsService
import nz.co.test.transactions.domain.entity.Transaction
import nz.co.test.transactions.domain.repository.TransactionsRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionsRepositoryImplTest {

    private lateinit var underTest: TransactionsRepository

    private val transactionsService = mock<TransactionsService>()

    private val transaction1 = Transaction(
        id = 1,
        transactionDate = OffsetDateTime.of(
            2022,
            11,
            5,
            8,
            45,
            12,
            0,
            ZoneOffset.ofHours(1)
        ),
        summary = "Summary 1",
        debit = BigDecimal.valueOf(3456),
        credit = BigDecimal.ZERO,
    )

    private val transaction2 = Transaction(
        id = 1,
        transactionDate = OffsetDateTime.of(
            2024,
            1,
            20,
            18,
            19,
            0,
            0,
            ZoneOffset.ofHours(1)
        ),
        summary = "Summary 1",
        debit = BigDecimal.ZERO,
        credit = BigDecimal.valueOf(2345.6),
    )

    private val transactionsList = listOf(transaction1, transaction2)

    @BeforeAll
    fun setUp() {
        underTest = TransactionsRepositoryImpl(
            transactionsService = transactionsService,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @BeforeEach
    fun resetMocks() {
        reset(transactionsService)
    }
    @Test
    fun `test that fetchTransactions successfully returns list of transactions`() = runTest {
        whenever(transactionsService.fetchTransactions()).thenReturn(transactionsList)

        assertThat(underTest.fetchTransactions()).isEqualTo(transactionsList)
    }
}