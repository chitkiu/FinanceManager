package korotchenko.financemanager.di

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import dagger.Module
import dagger.Provides
import korotchenko.financemanager.data.db.DBHelper

@Module
class DataModule {

    @Provides
    fun context(app: Application): Context = app.baseContext

    @Provides
    fun dbHelper(context: Context): SQLiteOpenHelper = DBHelper(context)
}