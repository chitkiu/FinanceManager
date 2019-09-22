package korotchenko.financemanager.presentation.fragment


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import korotchenko.financemanager.data.AccountDataRepository
import korotchenko.financemanager.R
import korotchenko.financemanager.data.wearable.TransferDataManager
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.fragment.adapters.AccountsAdapter
import korotchenko.logic.models.AccountModel
import kotlinx.android.synthetic.main.fragment_accounts.*
import javax.inject.Inject

class AccountsFragment : BaseFragment() {

    @Inject
    lateinit var accountDataRepository: AccountDataRepository

    @Inject
    lateinit var transferDataManager: TransferDataManager

    override val layoutID: Int = R.layout.fragment_accounts

    private val onAccountClick: (AccountModel) -> Unit = { account ->
        Toast.makeText(context, "Select ${account.name}!", Toast.LENGTH_LONG).show()
    }

    private val onAccountLongClick: (AccountModel, View) -> Unit = { account, view ->
        val popUpMenu = PopupMenu(context!!, view)
        popUpMenu.menu.add("Delete").setOnMenuItemClickListener {
            accountDataRepository.deleteAccount(account)
            return@setOnMenuItemClickListener true
        }
        popUpMenu.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_add_account.setOnClickListener {
            showFragment(
                fragment = CreateNewAccountFragment.newInstance()
            )
        }

        accounts_list.layoutManager = LinearLayoutManager(context)
        val accountList = accountDataRepository.getAccountsList()
        transferDataManager.sendAccounts(accountList)
        accounts_list.adapter = AccountsAdapter(
            MONEY_SYMBOL,
            onAccountClick,
            onAccountLongClick,
            accountList
        )
    }

    companion object {
        private const val MONEY_SYMBOL = "₴"
        fun newInstance() = AccountsFragment()
    }
}
