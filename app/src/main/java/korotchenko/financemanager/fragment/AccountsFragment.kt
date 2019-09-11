package korotchenko.financemanager.fragment


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import korotchenko.financemanager.AccountDataRepository
import korotchenko.financemanager.R
import korotchenko.financemanager.base.BaseFragment
import korotchenko.financemanager.fragment.adapters.AccountsAdapter
import kotlinx.android.synthetic.main.fragment_accounts.*

class AccountsFragment : BaseFragment() {

    private val accountDataRepository: AccountDataRepository = AccountDataRepository.getInstance()

    override val layoutID: Int = R.layout.fragment_accounts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_add_account.setOnClickListener {
            showFragment(
                fragment = CreateNewAccountFragment.newInstance()
            )
        }

        accounts_list.layoutManager = LinearLayoutManager(context)
        accounts_list.adapter = AccountsAdapter(MONEY_SYMBOL, { account ->
            Toast.makeText(context, "Select ${account.name}!", Toast.LENGTH_LONG).show()
        }, accountDataRepository.getAccountsList())
    }

    companion object {
        private const val MONEY_SYMBOL = "₴"
        fun newInstance() = AccountsFragment()
    }
}
