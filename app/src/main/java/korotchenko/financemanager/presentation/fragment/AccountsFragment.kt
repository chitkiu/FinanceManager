package korotchenko.financemanager.presentation.fragment


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxkotlin.plusAssign
import korotchenko.financemanager.data.AccountDataRepository
import korotchenko.financemanager.R
import korotchenko.financemanager.data.wearable.TransferDataManager
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.communicators.*
import korotchenko.financemanager.presentation.fragment.adapters.AccountsAdapter
import kotlinx.android.synthetic.main.fragment_accounts.*
import javax.inject.Inject

class AccountsFragment : BaseFragment() {

    private lateinit var accountAdapter: AccountsAdapter

    @Inject
    lateinit var accountDataRepository: AccountDataRepository

    @Inject
    lateinit var transferDataManager: TransferDataManager

    @Inject
    lateinit var accountActionCommunicator: AccountActionCommunicator

    override val layoutID: Int = R.layout.fragment_accounts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_add_account.setOnClickListener {
            showFragment(
                fragment = CreateNewAccountFragment.newInstance(),
                addInBackStack = true,
                shouldAddOrReplace = true
            )
        }

        accounts_list.layoutManager = LinearLayoutManager(context)
        val accountList = accountDataRepository.getAccountsList()
        accountAdapter = AccountsAdapter(
            MONEY_SYMBOL,
            accountActionCommunicator,
            accountList
        )
        transferDataManager.sendAccounts(accountList)
        accounts_list.adapter = accountAdapter
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable += accountActionCommunicator
            .observeAction()
            .subscribe(::handleAccountUpdate, ::handleError)
    }

    private fun handleAccountUpdate(accountAction: AccountAction) {
        when(accountAction) {
            is AccountSelect -> {
                Toast.makeText(context, "Select ${accountAction.accountModel.name}!", Toast.LENGTH_LONG).show()
            }
            is AccountDelete -> {
                accountDataRepository.deleteAccount(accountAction.accountModel)
                val accountList = accountDataRepository.getAccountsList()
                accountAdapter.setDate(accountList)
                transferDataManager.sendAccounts(accountList)
                accountAdapter.notifyDataSetChanged()
            }
            is AccountCreate -> {
                accountDataRepository.saveAccount(accountAction.accountModel)
                val accountList = accountDataRepository.getAccountsList()
                accountAdapter.setDate(accountList)
                transferDataManager.sendAccounts(accountList)
                accountAdapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val MONEY_SYMBOL = "₴"
        fun newInstance() = AccountsFragment()
    }
}
