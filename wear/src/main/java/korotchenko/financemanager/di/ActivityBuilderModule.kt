package korotchenko.financemanager.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import korotchenko.financemanager.presentation.activity.AccountDetailsActivity
import korotchenko.financemanager.presentation.activity.AccountsActivity
import korotchenko.financemanager.presentation.activity.MainActivity

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun accountsActivity(): AccountsActivity

    @ContributesAndroidInjector
    abstract fun accountDetailsActivity(): AccountDetailsActivity
}
