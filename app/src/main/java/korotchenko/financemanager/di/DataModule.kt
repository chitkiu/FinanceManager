package korotchenko.financemanager.di

import dagger.Module
import dagger.Provides
import korotchenko.financemanager.data.AccountDataRepository
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun accountDataRepository(): AccountDataRepository =
        AccountDataRepository()
}