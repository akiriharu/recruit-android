package nz.co.test.transactions.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nz.co.test.transactions.domain.entity.TransactionExtended
import nz.co.test.transactions.domain.usecase.GetTransactionsListUseCase
import nz.co.test.transactions.presentation.model.AmountType
import nz.co.test.transactions.presentation.model.UITransaction
import nz.co.test.transactions.presentation.model.mapper.UITransactionMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDateTime


@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionsViewModelTest {
    private lateinit var underTest: TransactionsViewModel

    private val getTransactionsListUseCase = mock<GetTransactionsListUseCase>()
    private val uiTransactionMapper = mock<UITransactionMapper>()

    private val extendedTransaction1 = TransactionExtended(
        id = 1,
        transactionDate = LocalDateTime.of(2024, 7, 29, 5, 16),
        summary = "Summary 1",
        debit = BigDecimal.ZERO,
        credit = BigDecimal.valueOf(2345.6),
        gst = BigDecimal.valueOf(351.84),
        isDebit = false
    )

    private val extendedTransaction2 = TransactionExtended(
        id = 2,
        transactionDate = LocalDateTime.of(2025, 1, 15, 14, 36),
        summary = "Summary 2",
        debit = BigDecimal.valueOf(3456),
        credit = BigDecimal.ZERO,
        gst = BigDecimal.valueOf(518.4),
        isDebit = true
    )

    private val extendedTransactionsList = listOf(extendedTransaction1, extendedTransaction2)

    private val uiTransaction1 = UITransaction(
        id = 1,
        summary = "Summary 1",
        transactionDate = "29/07/24 05:16",
        amount = "+$2345.60",
        amountType = AmountType.CREDIT,
        gst = "351.84"
    )

    private val uiTransaction2 = UITransaction(
        id = 2,
        summary = "Summary 2",
        transactionDate = "15/01/25 14:36",
        amount = "-$3456.00",
        amountType = AmountType.DEBIT,
        gst = "518.40"
    )

    private val uiTransactionList = listOf(uiTransaction1, uiTransaction2)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        reset(
            getTransactionsListUseCase,
            uiTransactionMapper,
            )
    }

    @AfterEach
    fun resetDispatcher() {
        Dispatchers.resetMain()
    }

    private fun initViewModel() {
        underTest = TransactionsViewModel(
            getTransactionsListUseCase,
            uiTransactionMapper,
        )
    }

    @Test
    fun `test that list of transactions is successfully fetched and mapped`() = runTest {
        whenever(getTransactionsListUseCase()).thenReturn(extendedTransactionsList)
        whenever(uiTransactionMapper.invoke(extendedTransaction1)).thenReturn(uiTransaction1)
        whenever(uiTransactionMapper.invoke(extendedTransaction2)).thenReturn(uiTransaction2)

        initViewModel()

        underTest.state.test {
            assertThat(awaitItem().transactionsList).isEqualTo(uiTransactionList)
        }
    }

    @Test
    fun `test that transactionsList in state is empty when there is an error fetching the list`() = runTest {
        whenever(getTransactionsListUseCase()).thenThrow(RuntimeException())

        assertDoesNotThrow { initViewModel() }

        underTest.state.test {
            assertThat(awaitItem().transactionsList).isEmpty()
        }
    }
}