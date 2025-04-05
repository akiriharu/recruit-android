package nz.co.test.transactions.domain.usecase

import nz.co.test.transactions.domain.entity.TransactionExtended
import nz.co.test.transactions.domain.repository.TransactionsRepository
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
        transactionsRepository.fetchTransactions().sortedByDescending { it.transactionDate }
}