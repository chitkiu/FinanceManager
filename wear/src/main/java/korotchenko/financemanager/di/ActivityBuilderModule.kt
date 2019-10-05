package korotchenko.financemanager.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import korotchenko.financemanager.presentation.activity.MainActivity
import korotchenko.financemanager.presentation.fragment.AccountDetailFragment
import korotchenko.financemanager.presentation.fragment.AccountsFragment
import korotchenko.financemanager.presentation.fragment.DashboardFragment

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun dashboardFragment(): DashboardFragment


    @ContributesAndroidInjector
    abstract fun accountsFragment(): AccountsFragment

    @ContributesAndroidInjector
    abstract fun accountDetailFragment(): AccountDetailFragment
}
