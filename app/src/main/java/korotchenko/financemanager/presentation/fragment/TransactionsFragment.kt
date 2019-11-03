package korotchenko.financemanager.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.base.BaseView
import korotchenko.financemanager.presentation.fragment.adapters.TransactionAdapter
import korotchenko.financemanager.presentation.presenters.TransactionsPresenter
import korotchenko.common.models.TransactionModel
import kotlinx.android.synthetic.main.fragment_transactions.*

interface TransactionsView : BaseView {
    fun setTransactions(transactions: List<TransactionModel>)
}

class TransactionsFragment : BaseFragment<TransactionsPresenter>(), TransactionsView {

    private lateinit var transactionsAdapter: TransactionAdapter

    override val layoutID: Int = R.layout.fragment_transactions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabAddExpense.setOnClickListener {
            showFragment(
                AddTransactionFragment.newInstance()
            )
        }

        transactionsAdapter = TransactionAdapter({})

        expensesList.layoutManager = LinearLayoutManager(context)
        expensesList.adapter = transactionsAdapter
    }

    override fun setTransactions(transactions: List<TransactionModel>) {
        transactionsAdapter.setTransactions(transactions)
        transactionsAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance() = TransactionsFragment()
    }
}
