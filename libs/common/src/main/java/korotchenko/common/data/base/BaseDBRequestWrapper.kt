package korotchenko.common.data.base

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import korotchenko.common.data.db.DBTable

abstract class BaseDBRequestWrapper<U: Any, T : DBTable<U>> constructor(
    private val dbHelper: SQLiteOpenHelper
) : BaseRequestWrapper<U>() {

    protected abstract val objectDBTable: T

    override fun getAllModel(): List<U> {
        val readableDatabase = dbHelper.readableDatabase
        val list = mutableListOf<U>()
        val cursor = readableDatabase.query(objectDBTable.tableName, null, null, null, null, null, null)
        if(cursor.moveToFirst()) {
            do {
                getTransaction(cursor)?.let(list::add)
            } while (cursor.moveToNext())
        }
        cursor.close()
        readableDatabase.close()
        return list
    }

    override fun getModelById(id: Long): U? {
        val readableDatabase = dbHelper.readableDatabase
        val cursor = readableDatabase.query(objectDBTable.tableName, null, "id = ?", arrayOf(id.toString()), null, null, null)
        if(cursor.moveToFirst()) {
            return getTransaction(cursor)
        }
        cursor.close()
        readableDatabase.close()
        return null
    }

    override fun save(model: U) {
        val writableDatabase = dbHelper.writableDatabase
        writableDatabase.insert(objectDBTable.tableName, null, getContentValues(model))
        writableDatabase.close()
    }

    override fun saveAll(list: List<U>) {
        val writableDatabase = dbHelper.writableDatabase
        writableDatabase.beginTransaction()
        list.forEach { account ->
            writableDatabase.insert(objectDBTable.tableName, null, getContentValues(account))
        }
        writableDatabase.setTransactionSuccessful()
        writableDatabase.close()
    }

    override fun deleteById(id: Long) {
        val writableDatabase = dbHelper.writableDatabase
        writableDatabase.delete(objectDBTable.tableName, "id = ?", arrayOf(id.toString()))
        writableDatabase.close()
    }

    protected fun getContentValues(model: U): ContentValues = objectDBTable.getContentValues(model)

    protected fun getTransaction(cursor: Cursor): U? {
        if(cursor.isClosed) {
            return null
        }
        return objectDBTable.loadFromCursor(cursor)
    }
}