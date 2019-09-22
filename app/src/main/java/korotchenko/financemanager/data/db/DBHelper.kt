package korotchenko.financemanager.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper: SQLiteOpenHelper {

    constructor(context: Context): super(context, DB_NAME, null, DB_VERSION)

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DB_CREATE_SQL_REQUEST)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(DB_REMOVE_SQL_REQUEST)
    }

    companion object {
        private const val DB_NAME = "db"

        private const val DB_VERSION = 1

        private const val DB_CREATE_SQL_REQUEST = "CREATE TABLE IF NOT EXISTS accounts(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "description TEXT, " +
                "balance REAL NOT NULL " +
                ");"

        private const val DB_REMOVE_SQL_REQUEST = "DROP TABLE IF EXISTS accounts;"
    }
}