package korotchenko.financemanager.presentation.activity

import androidx.wear.widget.WearableLinearLayoutManager
import com.google.android.gms.wearable.*
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.adapters.AccountsAdapter
import korotchenko.financemanager.presentation.base.BaseActivity
import korotchenko.logic.models.AccountModel
import korotchenko.logic.presenter.AccountModelMapper
import kotlinx.android.synthetic.main.activity_account.*

class AccountsActivity : BaseActivity(), DataClient.OnDataChangedListener  {

    override val layoutID: Int = R.layout.activity_account

    private lateinit var accountsAdapter: AccountsAdapter

    private val accountModelMapper: AccountModelMapper = AccountModelMapper()

    override fun onResume() {
        super.onResume()
        Wearable.getDataClient(this).addListener(this)
        accounts_list.layoutManager = WearableLinearLayoutManager(this)
        accountsAdapter = AccountsAdapter(MONEY_SYMBOL)
        accounts_list.adapter = accountsAdapter
        accounts_list.isEdgeItemsCenteringEnabled = true
    }

    override fun onPause() {
        super.onPause()
        Wearable.getDataClient(this).removeListener(this)
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            when(event.type) {
                DataEvent.TYPE_CHANGED -> handleAccountList(event)
            }
        }
    }

    private fun handleAccountList(event: DataEvent) {
        val path = event.dataItem.uri.path
        if(path == ACCOUNT_URL) {
            val dataMapItem = DataMapItem.fromDataItem(event.dataItem)
            dataMapItem.dataMap
                .getDataMapArrayList(ACCOUNT_KEY)
                .map(accountModelMapper::fromDataMap)
                .takeIf {
                    it.forEach { account ->
                        if (account == null)
                            return@takeIf false
                    }
                    return@takeIf true
                }
                ?.map { it as AccountModel }
                ?.let(accountsAdapter::setDate)
        }
    }

    companion object {
        private const val MONEY_SYMBOL = "₴"
        private const val ACCOUNT_URL = "/accounts"
        private const val ACCOUNT_KEY = "all_account"
    }
}