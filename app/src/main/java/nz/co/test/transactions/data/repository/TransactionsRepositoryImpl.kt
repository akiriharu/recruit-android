package nz.co.test.transactions.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import nz.co.test.transactions.data.services.TransactionsService
import nz.co.test.transactions.data.entity.Transaction
import nz.co.test.transactions.data.mapper.TransactionExtendedMapper
import nz.co.test.transactions.domain.entity.TransactionExtended
import nz.co.test.transactions.domain.qualifier.IoDispatcher
import nz.co.test.transactions.domain.repository.TransactionsRepository
import javax.inject.Inject

/**
 * [TransactionsRepository] implementation
 */
class TransactionsRepositoryImpl @Inject constructor(
    private val transactionsService: TransactionsService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val transactionExtendedMapper: TransactionExtendedMapper,
) : TransactionsRepository {
    override suspend fun fetchTransactions(): List<TransactionExtended> = withContext(ioDispatcher) {
        transactionsService.fetchTransactions().map { transactionExtendedMapper(it) }
    }
}