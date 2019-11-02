package korotchenko.financemanager.data

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import korotchenko.common.data.base.BaseDataRepository
import korotchenko.financemanager.data.db.TransactionsDBRequestWrapper
import korotchenko.common.models.TransactionModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionsDataRepository @Inject constructor(
    transactionsDBRequestWrapper: TransactionsDBRequestWrapper
) : BaseDataRepository<TransactionModel>(transactionsDBRequestWrapper, Schedulers.io(), AndroidSchedulers.mainThread())