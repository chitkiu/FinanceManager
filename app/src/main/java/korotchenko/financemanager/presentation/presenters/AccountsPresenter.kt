package korotchenko.financemanager.presentation.presenters

import korotchenko.financemanager.data.AccountDataRepository
import korotchenko.financemanager.data.wearable.TransferDataManager
import korotchenko.financemanager.presentation.base.BasePresenter
import korotchenko.financemanager.presentation.communicators.*
import korotchenko.financemanager.presentation.fragment.AccountsView
import javax.inject.Inject


class AccountsPresenter @Inject constructor(
    private var accountDataRepository: AccountDataRepository,
    private var transferDataManager: TransferDataManager,
    private var accountActionCommunicator: AccountActionCommunicator
) : BasePresenter<AccountsView>() {

    override fun onViewAttach(isFirstAttach: Boolean) {
        if(isFirstAttach) {
            updateAccountsList()
        }

        accountActionCommunicator
            .observeAction()
            .safeSubscribe(::handleAccountUpdate)
    }

    private fun handleAccountUpdate(accountAction: AccountAction) {
        when(accountAction) {
            is AccountSelect -> {
                view?.selectAccount(accountAction.accountModel)
            }
            is AccountDelete -> {
                accountDataRepository
                    .delete(accountAction.accountModel)
                    .safeSubscribe {
                        updateAccountsList()
                    }
            }
            is AccountCreate -> {
                accountDataRepository
                    .saveModel(accountAction.accountModel)
                    .safeSubscribe {
                        updateAccountsList()
                    }
            }
        }
    }

    private fun updateAccountsList() {
        accountDataRepository
            .getAllModels()
            .safeSubscribe { accountsList ->
                view?.showAccounts(accountsList)
                transferDataManager.sendAccounts(accountsList)
            }
    }
}
