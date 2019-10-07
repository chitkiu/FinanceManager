package korotchenko.financemanager.data

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import korotchenko.financemanager.data.db.AccountsDBRequestWrapper
import korotchenko.logic.models.AccountModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountDataRepository @Inject constructor(
    private val accountsDbRequestWrapper: AccountsDBRequestWrapper
) {

    fun getAccountsList(): Single<List<AccountModel>> = Single.fromCallable {
        accountsDbRequestWrapper.getAllAccountFromDB()
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun saveAccount(model: AccountModel): Completable = Completable.fromRunnable {
        accountsDbRequestWrapper.saveAccount(model)
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun saveAccounts(accounts: List<AccountModel>): Completable = Completable.fromRunnable {
        accountsDbRequestWrapper.saveAccounts(accounts)
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    fun deleteAccount(model: AccountModel): Completable = Completable.fromRunnable {
        accountsDbRequestWrapper.deleteAccount(model)
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    fun deleteAccountById(id: Long): Completable = Completable.fromRunnable {
        accountsDbRequestWrapper.deleteAccountById(id)
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}