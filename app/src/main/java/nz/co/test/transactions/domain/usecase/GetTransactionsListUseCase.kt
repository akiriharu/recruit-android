package nz.co.test.transactions.domain.usecase

import nz.co.test.transactions.domain.entity.Transaction
import nz.co.test.transactions.domain.entity.TransactionExtended
import nz.co.test.transactions.domain.repository.TransactionsRepository
import java.math.BigDecimal
import javax.inject.Inject

/**
 * The use case to get the list of transactions and convert each transaction into extended version
 * The list will be returned in descending order
 *
 * @property transactionsRepository The repository related to transactions
 */

class GetTransactionsListUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {
    suspend operator fun invoke(): List<TransactionExtended> =
        transactionsRepository.fetchTransactions().map { transaction ->
            TransactionExtended(
                id = transaction.id,
                transactionDate = transaction.transactionDate,
                summary = transaction.summary,
                debit = transaction.debit,
                credit = transaction.credit,
                gst = calculateGst(transaction),
                isDebit = transaction.debit.signum() != 0
            )
        }.sortedByDescending { it.transactionDate }

    private fun calculateGst(transaction: Transaction): BigDecimal =
        if (transaction.credit.signum() == 0)
            transaction.debit.multiply(BigDecimal(0.15))
        else
            transaction.credit.multiply(BigDecimal(0.15))
}