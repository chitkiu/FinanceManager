package korotchenko.common.data.db

import android.content.ContentValues
import android.database.Cursor

abstract class DBTable<T>(
    val tableName: String
) {
    abstract fun loadFromCursor(cursor: Cursor): T?
    abstract fun getContentValues(obj: T): ContentValues
}