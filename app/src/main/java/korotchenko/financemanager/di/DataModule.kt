package korotchenko.financemanager.di

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.Wearable
import dagger.Module
import dagger.Provides
import korotchenko.financemanager.data.db.DBHelper

@Module
class DataModule {

    @Provides
    fun context(app: Application): Context = app.baseContext

    @Provides
    fun dbHelper(context: Context): SQLiteOpenHelper = DBHelper(context)

    @Provides
    fun wearableDataClient(context: Context): DataClient = Wearable.getDataClient(context)

    @Provides
    fun googleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }
}