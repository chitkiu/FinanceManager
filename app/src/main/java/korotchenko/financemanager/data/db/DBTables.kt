package korotchenko.financemanager.data.db

import android.content.ContentValues
import android.database.Cursor
import androidx.core.database.getStringOrNull
import korotchenko.logic.models.AccountModel

sealed class DBTable<T>(
    val tableName: String
) {
    abstract fun loadFromCursor(cursor: Cursor): T?
    abstract fun getContentValues(obj: T): ContentValues
}

object AccountsTable: DBTable<AccountModel>("accounts") {
    override fun loadFromCursor(cursor: Cursor): AccountModel? {
        val idColumn = cursor.getColumnIndex("id")
        val nameColumn = cursor.getColumnIndex("name")
        val descriptionColumn = cursor.getColumnIndex("description")
        val balanceColumn = cursor.getColumnIndex("balance")

        return AccountModel(
            id = cursor.getLong(idColumn),
            name = cursor.getString(nameColumn),
            description = cursor.getStringOrNull(descriptionColumn),
            balance = cursor.getDouble(balanceColumn)
        )
    }

    override fun getContentValues(obj: AccountModel): ContentValues {
        val contentValues = ContentValues()
        contentValues.put("id", obj.id)
        contentValues.put("name", obj.name)
        contentValues.put("description", obj.description)
        contentValues.put("balance", obj.balance)
        return contentValues
    }

}