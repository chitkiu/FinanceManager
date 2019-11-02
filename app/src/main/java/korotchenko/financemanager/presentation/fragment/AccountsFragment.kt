package korotchenko.financemanager.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.base.BaseView
import korotchenko.financemanager.presentation.communicators.AccountActionCommunicator
import korotchenko.financemanager.presentation.fragment.adapters.AccountsAdapter
import korotchenko.financemanager.presentation.presenters.AccountsPresenter
import korotchenko.common.models.AccountModel
import kotlinx.android.synthetic.main.fragment_accounts.*
import javax.inject.Inject

interface AccountsView : BaseView {
    fun showAccounts(accountsList: List<AccountModel>)
    fun selectAccount(accountModel: AccountModel)
}

class AccountsFragment : BaseFragment<AccountsPresenter>(), AccountsView {

    private lateinit var accountAdapter: AccountsAdapter

    @Inject
    lateinit var accountActionCommunicator: AccountActionCommunicator

    override val layoutID: Int = R.layout.fragment_accounts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_add_account.setOnClickListener {
            showFragment(
                fragment = CreateNewAccountFragment.newInstance(),
                blockFragmentRepeat = true,
                addInBackStack = true,
                shouldAddOrReplace = true
            )
        }

        accounts_list.layoutManager = LinearLayoutManager(context)
        accountAdapter = AccountsAdapter(
            MONEY_SYMBOL,
            accountActionCommunicator
        )
        accounts_list.adapter = accountAdapter
    }

    override fun showAccounts(accountsList: List<AccountModel>) {
        accountAdapter.setDate(accountsList)
    }

    override fun selectAccount(accountModel: AccountModel) {
        Toast.makeText(context, "Select ${accountModel.name}!", Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val MONEY_SYMBOL = "₴"
        fun newInstance() = AccountsFragment()
    }
}
