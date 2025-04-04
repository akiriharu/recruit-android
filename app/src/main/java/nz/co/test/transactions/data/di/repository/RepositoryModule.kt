package nz.co.test.transactions.data.di.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nz.co.test.transactions.data.repository.TransactionsRepositoryImpl
import nz.co.test.transactions.domain.repository.TransactionsRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    abstract fun bindTransactionsRepository(impl: TransactionsRepositoryImpl): TransactionsRepository
}