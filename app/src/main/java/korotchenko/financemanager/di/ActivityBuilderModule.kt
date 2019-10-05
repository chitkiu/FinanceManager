package korotchenko.financemanager.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import korotchenko.financemanager.presentation.activity.MainActivity
import korotchenko.financemanager.presentation.fragment.*

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun accountsFragment(): AccountsFragment

    @ContributesAndroidInjector
    abstract fun createNewAccountFragment(): CreateNewAccountFragment

    @ContributesAndroidInjector
    abstract fun signInFragment(): SignInFragment

    @ContributesAndroidInjector
    abstract fun expensesFragment(): ExpensesFragment

    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun transactionFragment(): TransactionFragment

    @ContributesAndroidInjector
    abstract fun dashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun settingsFragment(): SettingsFragment
}