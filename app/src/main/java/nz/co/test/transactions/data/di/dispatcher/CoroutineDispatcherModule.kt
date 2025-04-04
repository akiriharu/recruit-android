package nz.co.test.transactions.data.di.dispatcher

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import nz.co.test.transactions.domain.qualifier.IoDispatcher

/**
 * Module, which is providing Coroutine Dispatcher
 */
@Module
@InstallIn(SingletonComponent::class)
class CoroutineDispatcherModule {
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}