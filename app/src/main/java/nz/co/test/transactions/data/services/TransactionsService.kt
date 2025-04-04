package nz.co.test.transactions.data.services

import nz.co.test.transactions.domain.entity.Transaction
import retrofit2.http.GET

/**
 * Retrofit service class to define API endpoints related to Transactions
 */
interface TransactionsService {
    /**
     * Fetch the list of Transactions
     *
     * @return Transactions' list
     */
    @GET("Josh-Ng/500f2716604dc1e8e2a3c6d31ad01830/raw/4d73acaa7caa1167676445c922835554c5572e82/test-data.json")
    suspend fun fetchTransactions(): List<Transaction>
}

