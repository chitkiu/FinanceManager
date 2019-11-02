package korotchenko.financemanager.di

import dagger.Module
import dagger.Provides
import korotchenko.common.presenter.AccountModelMapper

@Module
class LogicModule {

    @Provides
    fun accountMapper(): AccountModelMapper = AccountModelMapper()
}