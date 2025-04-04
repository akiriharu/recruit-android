package nz.co.test.transactions.domain.repository

import nz.co.test.transactions.domain.entity.Transaction

/**
 * The repository related to operations for Transactions
 */
interface TransactionsRepository {
    /**
     * Fetch the list of Transactions
     *
     * @return Transactions' list
     */
    suspend fun fetchTransactions(): List<Transaction>
}