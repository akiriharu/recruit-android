package nz.co.test.transactions.data.services

import nz.co.test.transactions.domain.entity.Transaction
import retrofit2.http.GET

interface TransactionsService {
    @GET("transactions")
    suspend fun retrieveTransactions(): Array<Transaction>
}

