package korotchenko.financemanager.di

import android.app.Application
import android.content.Context
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.Wearable
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun context(app: Application): Context = app.baseContext

    @Provides
    fun wearableDataClient(context: Context): DataClient = Wearable.getDataClient(context)
}