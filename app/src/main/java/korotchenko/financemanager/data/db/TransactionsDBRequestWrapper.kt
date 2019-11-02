package korotchenko.financemanager.data.db

import android.database.sqlite.SQLiteOpenHelper
import korotchenko.common.data.base.BaseDBRequestWrapper
import korotchenko.common.models.TransactionModel
import javax.inject.Inject

class TransactionsDBRequestWrapper @Inject constructor(
    dbHelper: SQLiteOpenHelper
) : BaseDBRequestWrapper<TransactionModel, TransactionsTable>(dbHelper) {

    override val objectDBTable: TransactionsTable = TransactionsTable

    override fun delete(model: TransactionModel) {
        deleteById(model.id)
    }
}