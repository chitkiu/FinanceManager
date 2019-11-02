package korotchenko.financemanager.data.db

import android.database.sqlite.SQLiteOpenHelper
import korotchenko.common.data.base.BaseDBRequestWrapper
import korotchenko.common.models.AccountModel
import javax.inject.Inject

class AccountsDBRequestWrapper @Inject constructor(
    dbHelper: SQLiteOpenHelper
) : BaseDBRequestWrapper<AccountModel, AccountsTable>(dbHelper) {

    override val objectDBTable: AccountsTable = AccountsTable

    override fun delete(model: AccountModel) {
        deleteById(model.id)
    }

}