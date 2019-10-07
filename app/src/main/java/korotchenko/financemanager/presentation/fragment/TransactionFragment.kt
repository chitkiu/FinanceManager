package korotchenko.financemanager.presentation.fragment


import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.base.BaseView
import korotchenko.financemanager.presentation.presenters.TransactionPresenter

interface TransactionView : BaseView {

}

class TransactionFragment : BaseFragment<TransactionPresenter>(), TransactionView {

    override val layoutID: Int = R.layout.fragment_transaction

    companion object {
        fun newInstance() = TransactionFragment()
    }
}
