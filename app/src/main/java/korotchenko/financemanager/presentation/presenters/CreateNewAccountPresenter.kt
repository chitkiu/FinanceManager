package korotchenko.financemanager.presentation.presenters

import korotchenko.financemanager.presentation.base.BasePresenter
import korotchenko.financemanager.presentation.communicators.AccountActionCommunicator
import korotchenko.financemanager.presentation.communicators.AccountCreate
import korotchenko.financemanager.presentation.fragment.CreateNewAccountFragment
import korotchenko.financemanager.presentation.fragment.CreateNewAccountView
import korotchenko.logic.models.AccountModel
import javax.inject.Inject

class CreateNewAccountPresenter @Inject constructor(
    private var accountActionCommunicator: AccountActionCommunicator
) : BasePresenter<CreateNewAccountView>() {

    fun createNewAccount(accountModel: AccountModel) {
        hideAllError()
        if(accountModel.balance == CreateNewAccountFragment.EMPTY_BALANCE_VALUE) {
            view?.showBalanceError("Cannot be blank!")
            return
        }
        if(accountModel.name.isBlank()) {
            view?.showNameError("Cannot be blank!")
            return
        }
        accountActionCommunicator.sendAction(AccountCreate(accountModel))
        view?.goBackToMainScreen()
    }

    private fun hideAllError() {
        view?.showBalanceError(null)
        view?.showNameError(null)
    }

}