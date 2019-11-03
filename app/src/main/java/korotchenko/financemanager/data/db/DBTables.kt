package korotchenko.financemanager.data.db

import android.content.ContentValues
import android.database.Cursor
import korotchenko.common.data.db.DBTable
import korotchenko.common.models.AccountModel
import korotchenko.common.models.TransactionModel

object AccountsTable: DBTable<AccountModel>("accounts") {

    override fun loadFromCursor(cursor: Cursor): AccountModel? {
        val idColumn = cursor.getColumnIndex(AccountModel.ID_KEY)
        val nameColumn = cursor.getColumnIndex(AccountModel.NAME_KEY)
        val descriptionColumn = cursor.getColumnIndex(AccountModel.DESCRIPTION_KEY)
        val balanceColumn = cursor.getColumnIndex(AccountModel.BALANCE_KEY)

        return AccountModel(
            id = cursor.getLong(idColumn),
            name = cursor.getString(nameColumn),
            description = cursor.getString(descriptionColumn),
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

object TransactionsTable: DBTable<TransactionModel>("transactions") {

    override fun loadFromCursor(cursor: Cursor): TransactionModel? {
        val idColumn = cursor.getColumnIndex(TransactionModel.ID_KEY)
        val descriptionColumn = cursor.getColumnIndex(TransactionModel.DESCRIPTION_KEY)
        val sumColumn = cursor.getColumnIndex(TransactionModel.SUM_KEY)
        val transactionTypeIdColumn = cursor.getColumnIndex(TransactionModel.TRANSACTION_TYPE_ID_KEY)
        val categoryIdColumn = cursor.getColumnIndex(TransactionModel.CATEGORY_ID_KEY)
        val dateColumn = cursor.getColumnIndex(TransactionModel.DATE_KEY)

        return TransactionModel(
            id = cursor.getLong(idColumn),
            description = cursor.getString(descriptionColumn),
            sum = cursor.getDouble(sumColumn),
            transactionTypeId = cursor.getInt(transactionTypeIdColumn),
            categoryId = cursor.getLong(categoryIdColumn),
            date = cursor.getString(dateColumn)
        )
    }

    override fun getContentValues(obj: TransactionModel): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(TransactionModel.ID_KEY, obj.id)
        contentValues.put(TransactionModel.DESCRIPTION_KEY, obj.description)
        contentValues.put(TransactionModel.SUM_KEY, obj.sum)
        contentValues.put(TransactionModel.TRANSACTION_TYPE_ID_KEY, obj.transactionTypeId)
        contentValues.put(TransactionModel.CATEGORY_ID_KEY, obj.categoryId)
        contentValues.put(TransactionModel.DATE_KEY, obj.date)
        return contentValues
    }
}