package korotchenko.financemanager.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.data.ExpenseDataRepository
import korotchenko.financemanager.presentation.fragment.adapters.ExpensesAdapter
import kotlinx.android.synthetic.main.fragment_expenses.*
import javax.inject.Inject

class ExpensesFragment : BaseFragment() {

    @Inject
    lateinit var expenseDataRepository: ExpenseDataRepository

    override val layoutID: Int = R.layout.fragment_expenses

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_add_expense.setOnClickListener {
            showFragment(
                fragment = CreateNewAccountFragment.newInstance()
            )
        }

        expenses_list.layoutManager = LinearLayoutManager(context)
        expenses_list.adapter = ExpensesAdapter({}, expenseDataRepository.getExpenseList())
    }

    companion object {
        fun newInstance() = ExpensesFragment()
    }
}
