package korotchenko.financemanager.presentation.presenters

import korotchenko.financemanager.data.TransactionsDataRepository
import korotchenko.financemanager.presentation.base.BasePresenter
import korotchenko.financemanager.presentation.fragment.TransactionsView
import javax.inject.Inject

class TransactionsPresenter @Inject constructor(
    private val transactionsDataRepository: TransactionsDataRepository
) : BasePresenter<TransactionsView>() {

    override fun onViewAttach(isFirstAttach: Boolean) {
        super.onViewAttach(isFirstAttach)
        transactionsDataRepository
            .getAllModels()
            .safeSubscribe {
                view?.setTransactions(it)
            }
    }
}