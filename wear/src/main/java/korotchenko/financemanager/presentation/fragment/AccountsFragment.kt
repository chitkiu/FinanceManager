package korotchenko.financemanager.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.wear.widget.WearableLinearLayoutManager
import com.google.android.gms.wearable.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.adapters.AccountsAdapter
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.communicators.AccountAction
import korotchenko.financemanager.presentation.communicators.AccountActionCommunicator
import korotchenko.financemanager.presentation.communicators.AccountSelect
import korotchenko.logic.models.AccountModel
import korotchenko.logic.presenter.AccountModelMapper
import kotlinx.android.synthetic.main.activity_account.*
import javax.inject.Inject

class AccountsFragment : BaseFragment(), DataClient.OnDataChangedListener {

    override val layoutID: Int = R.layout.activity_account

    @Inject
    internal lateinit var accountActionCommunicator: AccountActionCommunicator

    private lateinit var accountsAdapter: AccountsAdapter

    private val accountModelMapper: AccountModelMapper = AccountModelMapper()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { nonNullContext ->
            Wearable.getDataClient(nonNullContext).apply {
                addListener(this@AccountsFragment)
                dataItems.addOnSuccessListener { it.forEach(::handleAccountList) }
            }
            accountsAdapter = AccountsAdapter(MONEY_SYMBOL, accountActionCommunicator)
            accounts_list.apply {
                layoutManager = WearableLinearLayoutManager(nonNullContext)
                adapter = accountsAdapter
                isEdgeItemsCenteringEnabled = true
            }
        }
    }

    override fun onResume() {
        Log.e(TAG, "onResume")
        super.onResume()
        accountActionCommunicator
            .observeAction()
            .safeSubscribe(::handleAccountAction)
    }

    override fun onPause() {
        Log.e(TAG, "onPause")
        super.onPause()
        context?.let { nonNullContext ->
            Wearable.getDataClient(nonNullContext).removeListener(this)
        }
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            when(event.type) {
                DataEvent.TYPE_CHANGED -> handleAccountList(event)
            }
        }
    }

    private fun handleAccountAction(action: AccountAction) {
        when(action) {
            is AccountSelect -> openAccountDetail(action.accountModel)
        }
    }

    private fun openAccountDetail(accountModel: AccountModel) {
        showFragment(
            fragment = AccountDetailFragment.newInstance(accountModel),
            addInBackStack = true,
            shouldAddOrReplace = true
        )
    }

    private fun handleAccountList(event: DataEvent) {
        handleAccountList(event.dataItem)
    }

    private fun handleAccountList(dataItem: DataItem) {
        val path = dataItem.uri.path
        if(path == ACCOUNT_URL) {
            Single.fromCallable {
                DataMapItem
                    .fromDataItem(dataItem)
                    .dataMap
                    .getDataMapArrayList(ACCOUNT_KEY)
            }
                .map { it.map(accountModelMapper::fromDataMap) }
                .filter {
                    it.forEach { account ->
                        if (account == null)
                            return@filter false
                    }
                    return@filter true
                }
                .map { list -> list.map { it as AccountModel } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(accountsAdapter::setDate)
        }
    }

    companion object {
        private const val MONEY_SYMBOL = "₴"
        private const val ACCOUNT_URL = "/accounts"
        private const val ACCOUNT_KEY = "all_account"

        fun newInstance(): AccountsFragment = AccountsFragment()
    }
}