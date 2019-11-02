package korotchenko.financemanager.data

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import korotchenko.common.data.base.BaseDataRepository
import korotchenko.financemanager.data.db.AccountsDBRequestWrapper
import korotchenko.common.models.AccountModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountDataRepository @Inject constructor(
    accountsDbRequestWrapper: AccountsDBRequestWrapper
) : BaseDataRepository<AccountModel>(accountsDbRequestWrapper, Schedulers.io(), AndroidSchedulers.mainThread())