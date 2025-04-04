package nz.co.test.transactions.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import nz.co.test.transactions.data.services.TransactionsService
import nz.co.test.transactions.domain.entity.Transaction
import nz.co.test.transactions.domain.qualifier.IoDispatcher
import nz.co.test.transactions.domain.repository.TransactionsRepository
import javax.inject.Inject

/**
 * [TransactionsRepository] implementation
 */
class TransactionsRepositoryImpl @Inject constructor(
    private val transactionsService: TransactionsService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : TransactionsRepository {
    override suspend fun fetchTransactions(): List<Transaction> = withContext(ioDispatcher) {
        transactionsService.fetchTransactions()
    }
}