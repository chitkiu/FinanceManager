package korotchenko.financemanager.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import korotchenko.logic.models.AccountModel

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

        private val DB_CREATE_SQL_REQUEST = "CREATE TABLE IF NOT EXISTS ${AccountsTable.tableName}(" +
                "${AccountModel.ID_KEY} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${AccountModel.NAME_KEY} TEXT NOT NULL, " +
                "${AccountModel.DESCRIPTION_KEY} TEXT, " +
                "${AccountModel.BALANCE_KEY} REAL NOT NULL " +
                ");"

        private val DB_REMOVE_SQL_REQUEST = "DROP TABLE IF EXISTS ${AccountsTable.tableName};"
    }
}