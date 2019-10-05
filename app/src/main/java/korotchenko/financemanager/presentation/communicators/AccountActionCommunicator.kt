package korotchenko.financemanager.presentation.communicators

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import korotchenko.logic.models.AccountModel
import javax.inject.Inject
import javax.inject.Singleton

sealed class AccountAction

data class AccountSelect(val accountModel: AccountModel): AccountAction()
data class AccountDelete(val accountModel: AccountModel): AccountAction()
data class AccountCreate(val accountModel: AccountModel): AccountAction()

@Singleton
class AccountActionCommunicator @Inject constructor(){

    private val accountEvent = PublishSubject.create<AccountAction>()

    fun sendAction(action: AccountAction) {
        accountEvent.onNext(action)
    }

    fun observeAction(): Observable<AccountAction> = accountEvent
}