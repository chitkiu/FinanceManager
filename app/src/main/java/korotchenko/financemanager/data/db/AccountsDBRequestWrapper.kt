package korotchenko.financemanager.data.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getStringOrNull
import korotchenko.logic.models.AccountModel
import javax.inject.Inject

class AccountsDBRequestWrapper @Inject constructor(
    private val dbHelper: SQLiteOpenHelper
) {

    fun getAllAccountFromDB(): List<AccountModel> {
        val readableDatabase = dbHelper.readableDatabase
        val listOfAcc = mutableListOf<AccountModel>()
        val cursor = readableDatabase.query(AccountsTable.tableName, null, null, null, null, null, null)
        if(cursor.moveToFirst()) {
            do {
                getAccount(cursor)?.let(listOfAcc::add)
            } while (cursor.moveToNext())
        }
        cursor.close()
        readableDatabase.close()
        return listOfAcc
    }

    fun saveAccount(account: AccountModel) {
        val writableDatabase = dbHelper.writableDatabase
        writableDatabase.insert(AccountsTable.tableName, null, getContentValues(account))
        writableDatabase.close()
    }

    fun saveAccounts(accounts: List<AccountModel>) {
        val writableDatabase = dbHelper.writableDatabase
        writableDatabase.beginTransaction()
        accounts.forEach { account ->
            writableDatabase.insert(AccountsTable.tableName, null, getContentValues(account))
        }
        writableDatabase.setTransactionSuccessful()
        writableDatabase.close()
    }

    fun deleteAccount(account: AccountModel) {
        deleteAccountById(account.id)
    }

    fun deleteAccountById(id: Long) {
        val writableDatabase = dbHelper.writableDatabase
        writableDatabase.delete(AccountsTable.tableName, "id = ?", arrayOf(id.toString()))
        writableDatabase.close()
    }

    private fun getContentValues(account: AccountModel): ContentValues = AccountsTable.getContentValues(account)

    private fun getAccount(cursor: Cursor): AccountModel? {
        if(cursor.isClosed) {
            return null
        }
        return AccountsTable.loadFromCursor(cursor)
    }
}