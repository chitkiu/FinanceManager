package korotchenko.financemanager.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import korotchenko.financemanager.presentation.App
import javax.inject.Singleton

@Component(modules = arrayOf(
    AndroidSupportInjectionModule::class,
    ActivityBuilderModule::class,
    DataModule::class
))
@Singleton
interface AppComponent: AndroidInjector<App> {

    override fun inject(app: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}