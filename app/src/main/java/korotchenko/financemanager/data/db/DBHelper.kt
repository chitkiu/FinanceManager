package korotchenko.financemanager.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import korotchenko.common.models.AccountModel
import korotchenko.common.models.TransactionModel

class DBHelper: SQLiteOpenHelper {

    constructor(context: Context): super(context, DB_NAME, null, DB_VERSION)

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ACCOUNT_TABLE_CREATE_SQL_REQUEST)
        db?.execSQL(TRANSACTION_TABLE_CREATE_SQL_REQUEST)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(ACCOUNT_TABLE_REMOVE_SQL_REQUEST)
        db?.execSQL(TRANSACTION_TABLE_REMOVE_SQL_REQUEST)

        db?.execSQL(ACCOUNT_TABLE_CREATE_SQL_REQUEST)
        db?.execSQL(TRANSACTION_TABLE_CREATE_SQL_REQUEST)
    }

    companion object {
        private const val DB_NAME = "db"

        private const val DB_VERSION = 3

        private val ACCOUNT_TABLE_CREATE_SQL_REQUEST = "CREATE TABLE IF NOT EXISTS ${AccountsTable.tableName}(" +
                "${AccountModel.ID_KEY} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${AccountModel.NAME_KEY} TEXT NOT NULL, " +
                "${AccountModel.DESCRIPTION_KEY} TEXT, " +
                "${AccountModel.BALANCE_KEY} REAL NOT NULL " +
                ");"

        private val ACCOUNT_TABLE_REMOVE_SQL_REQUEST = "DROP TABLE IF EXISTS ${AccountsTable.tableName};"

        private val TRANSACTION_TABLE_CREATE_SQL_REQUEST = "CREATE TABLE IF NOT EXISTS ${TransactionsTable.tableName}(" +
                "${TransactionModel.ID_KEY} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${TransactionModel.DESCRIPTION_KEY} TEXT, " +
                "${TransactionModel.SUM_KEY} REAL NOT NULL, " +
                "${TransactionModel.CATEGORY_ID_KEY} INTEGER NOT NULL, " +
                "${TransactionModel.DATE_KEY} TEXT NOT NULL " +
                ");"

        private val TRANSACTION_TABLE_REMOVE_SQL_REQUEST = "DROP TABLE IF EXISTS ${TransactionsTable.tableName};"

    }
}