package korotchenko.financemanager.presentation.fragment


import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment

class TransactionFragment : BaseFragment() {

    override val layoutID: Int = R.layout.fragment_transaction

    companion object {
        fun newInstance() = TransactionFragment()
    }
}
