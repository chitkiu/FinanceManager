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
        val idColumn = cursor.getColumnIndex(AccountModel.ID_KEY)
        val nameColumn = cursor.getColumnIndex(AccountModel.NAME_KEY)
        val descriptionColumn = cursor.getColumnIndex(AccountModel.DESCRIPTION_KEY)
        val balanceColumn = cursor.getColumnIndex(AccountModel.BALANCE_KEY)

        return AccountModel(
            id = cursor.getLong(idColumn),
            name = cursor.getString(nameColumn),
            description = cursor.getStringOrNull(descriptionColumn),
            balance = cursor.getDouble(balanceColumn)
        )
    }

    override fun getContentValues(obj: AccountModel): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(AccountModel.ID_KEY, obj.id)
        contentValues.put(AccountModel.NAME_KEY, obj.name)
        contentValues.put(AccountModel.DESCRIPTION_KEY, obj.description)
        contentValues.put(AccountModel.BALANCE_KEY, obj.balance)
        return contentValues
    }

}