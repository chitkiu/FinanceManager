package korotchenko.financemanager.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import korotchenko.financemanager.activity.MainActivity
import korotchenko.financemanager.fragment.AccountsFragment
import korotchenko.financemanager.fragment.CreateNewAccountFragment
import korotchenko.financemanager.fragment.SignInFragment

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
}