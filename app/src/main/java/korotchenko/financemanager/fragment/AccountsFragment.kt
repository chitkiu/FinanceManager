package korotchenko.financemanager.fragment


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import korotchenko.financemanager.data.AccountDataRepository
import korotchenko.financemanager.R
import korotchenko.financemanager.base.BaseFragment
import korotchenko.financemanager.fragment.adapters.AccountsAdapter
import korotchenko.logic.models.AccountModel
import kotlinx.android.synthetic.main.fragment_accounts.*
import javax.inject.Inject
import kotlin.random.Random

class AccountsFragment : BaseFragment() {

    @Inject
    lateinit var accountDataRepository: AccountDataRepository

    override val layoutID: Int = R.layout.fragment_accounts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: Delete this shit
        mockData()

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

    private fun mockData() {
        if(accountDataRepository.getAccountsList().isEmpty()) {
            val random = Random(System.currentTimeMillis())
            listOf(
                AccountModel(
                    id = System.currentTimeMillis(),
                    name = "Карта приват",
                    balance = random.nextDouble(-1000.0, 1000.0)
                ), AccountModel(
                    id = System.currentTimeMillis(),
                    name = "Карта моно",
                    balance = random.nextDouble(-1000.0, 1000.0)
                ), AccountModel(
                    id = System.currentTimeMillis(),
                    name = "Наличные",
                    balance = random.nextDouble(-1000.0, 1000.0)
                )
            ).forEach {
                accountDataRepository.saveAccount(it)
            }
        }
    }

    companion object {
        private const val MONEY_SYMBOL = "₴"
        fun newInstance() = AccountsFragment()
    }
}
