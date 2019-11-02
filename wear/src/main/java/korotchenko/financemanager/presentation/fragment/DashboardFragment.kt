package korotchenko.financemanager.presentation.fragment

import android.os.Bundle
import android.view.View
import com.google.android.gms.wearable.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.common.presenter.AccountModelMapper
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardFragment: BaseFragment(), DataClient.OnDataChangedListener {

    override val layoutID: Int = R.layout.activity_dashboard

    private val accountModelMapper: AccountModelMapper = AccountModelMapper()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { nonNullContext ->
            Wearable.getDataClient(nonNullContext).apply {
                addListener(this@DashboardFragment)
                dataItems.addOnSuccessListener { it.forEach(::handleAccountList) }
            }
        }
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            when(event.type) {
                DataEvent.TYPE_CHANGED -> handleAccountList(event)
            }
        }
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
                .map { accountList ->
                    return@map accountList.sumByDouble { it?.balance ?: 0.0 }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe {
                    totalBalanceTextView.text = String.format("%.2f %s", it, MONEY_SYMBOL)
                }
        }
    }


    companion object {
        private const val MONEY_SYMBOL = "₴"

        private const val ACCOUNT_URL = "/accounts"
        private const val ACCOUNT_KEY = "all_account"

        fun newInstance(): DashboardFragment = DashboardFragment()
    }
}